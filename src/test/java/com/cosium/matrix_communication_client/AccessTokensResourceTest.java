package com.cosium.matrix_communication_client;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.cosium.synapse_junit_extension.EnableSynapse;
import com.cosium.synapse_junit_extension.Synapse;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Réda Housni Alaoui
 */
@EnableSynapse
class AccessTokensResourceTest {
  private Synapse synapse;

  @BeforeEach
  void beforeEach(Synapse synapse) {
    this.synapse = synapse;
  }

  @Test
  @DisplayName("Create room")
  void test1() {
    String accessToken =
        newResourcesBuilder()
            .usernamePassword(synapse.adminUsername(), synapse.adminPassword())
            .build()
            .accessTokens()
            .create();

    CreateRoomInput createRoomInput =
        CreateRoomInput.builder()
            .name(UUID.randomUUID().toString())
            .roomAliasName(UUID.randomUUID().toString())
            .topic(UUID.randomUUID().toString())
            .build();
    RoomsResource rooms = newResourcesBuilder().accessToken(accessToken).build().rooms();
    assertThatCode(() -> rooms.create(createRoomInput)).doesNotThrowAnyException();
  }

  private MatrixResourcesFactory.AuthenticationBuilder newResourcesBuilder() {
    return MatrixResources.factory()
        .builder()
        .https(synapse.https())
        .hostname(synapse.hostname())
        .port(synapse.port());
  }
}
