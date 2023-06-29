package com.cosium.matrix_communication_client.test_infrastructure.synapse;

import static java.util.Objects.requireNonNull;

import com.cosium.matrix_communication_client.test_infrastructure.CloseableResource;
import com.cosium.matrix_communication_client.test_infrastructure.ObjectMappers;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Mount;
import com.github.dockerjava.api.model.VolumeOptions;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.ResourceReaper;

/**
 * @author Réda Housni Alaoui
 */
public class SynapseServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(SynapseServer.class);

  private static final int HTTP_PORT = 8008;

  private static final String IMAGE_NAME = "matrixdotorg/synapse:v1.86.0";

  private static final String NETWORK_ALIAS = "matrix-server";
  private static final String ADMIN_USERNAME = "admin_user";
  private static final String ADMIN_PASSWORD = "admin_secret";

  private static final Map<String, String> VOLUME_LABELS =
      Map.of(DockerClientFactory.TESTCONTAINERS_SESSION_ID_LABEL, DockerClientFactory.SESSION_ID);

  private final String volumeName;
  private final GenericContainer<?> container;
  private final String url;

  private SynapseServer() {
    volumeName = "jenkins-pipeline-library.matrix-server." + UUID.randomUUID();

    try (GenericContainer<?> transientContainer =
        createContainer(volumeName)
            .withCommand("generate")
            .waitingFor(
                new LogMessageWaitStrategy().withRegEx(".*A config file has been generated.*"))) {
      transientContainer.start();
    }

    container =
        createContainer(volumeName).withExposedPorts(HTTP_PORT).withNetworkAliases(NETWORK_ALIAS);
    container.start();

    url = "http://localhost:" + container.getMappedPort(HTTP_PORT);

    HomeServerConfig serverConfig =
        container.copyFileFromContainer(
            "/data/homeserver.yaml",
            inputStream -> ObjectMappers.forYaml().readValue(inputStream, HomeServerConfig.class));

    new SynapseClient(url)
        .createUser(serverConfig.registrationSharedSecret(), ADMIN_USERNAME, ADMIN_PASSWORD, true);
  }

  public static CloseableResource<SynapseServer> start() {
    SynapseServer server = new SynapseServer();
    return new CloseableResource<>() {

      @Override
      public SynapseServer resource() {
        return server;
      }

      @Override
      public void close() {
        server.stop();
      }
    };
  }

  private static GenericContainer<?> createContainer(String volumeName) {
    ResourceReaper.instance().registerLabelsFilterForCleanup(VOLUME_LABELS);

    return new GenericContainer<>(DockerImageName.parse(IMAGE_NAME))
        .withLogConsumer(new Slf4jLogConsumer(LOGGER))
        .withStartupTimeout(Duration.ofMinutes(30))
        .withCreateContainerCmdModifier(
            command -> {
              HostConfig hostConfig = command.getHostConfig();
              requireNonNull(hostConfig);
              hostConfig.withMounts(
                  List.of(
                      new Mount()
                          .withVolumeOptions(new VolumeOptions().withLabels(VOLUME_LABELS))
                          .withSource(volumeName)
                          .withTarget("/data")));
            })
        .withEnv("SYNAPSE_SERVER_NAME", NETWORK_ALIAS)
        .withEnv("SYNAPSE_REPORT_STATS", "no");
  }

  public int port() {
    return container.getMappedPort(HTTP_PORT);
  }

  public String url() {
    return url;
  }

  public String adminUsername() {
    return ADMIN_USERNAME;
  }

  public String adminPassword() {
    return ADMIN_PASSWORD;
  }

  public SynapseClient client() {
    return new SynapseClient(url);
  }

  private void stop() {
    container.stop();
  }

  private static class HomeServerConfig {

    private final String registrationSharedSecret;

    public HomeServerConfig(
        @JsonProperty("registration_shared_secret") String registrationSharedSecret) {
      this.registrationSharedSecret = registrationSharedSecret;
    }

    public String registrationSharedSecret() {
      return registrationSharedSecret;
    }
  }
}
