package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import com.cosium.matrix_communication_client.internal.JsonBody;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Optional;

/**
 * @author Réda Housni Alaoui
 */
class WellKnownMatrixClient {

  private final HomeServer homeServer;

  @JsonCreator
  WellKnownMatrixClient(@JsonProperty("m.homeserver") HomeServer homeServer) {
    this.homeServer = requireNonNull(homeServer);
  }

  public static Optional<WellKnownMatrixClient> fetch(
      HttpClient httpClient, JsonHandlers jsonHandlers, MatrixUris matrixUris) {
    MatrixUri uri = matrixUris.create(".well-known", "matrix", "client");
    try {
      JsonBody<WellKnownMatrixClient> response =
          httpClient
              .send(
                  HttpRequest.newBuilder(uri.toUri())
                      .header("Accept", "application/json")
                      .GET()
                      .build(),
                  jsonHandlers.handler(WellKnownMatrixClient.class))
              .body();
      if (response.isNotFound()) {
        return Optional.empty();
      }
      return Optional.of(response.parse());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  public HomeServer homeServer() {
    return homeServer;
  }

  public static class HomeServer {
    private final String baseUrl;

    @JsonCreator
    HomeServer(@JsonProperty("base_url") String baseUrl) {
      this.baseUrl = requireNonNull(baseUrl);
    }

    public MatrixUri baseUri() {
      return new MatrixUri(baseUrl);
    }
  }
}
