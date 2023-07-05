package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

/**
 * @author Réda Housni Alaoui
 */
class MatrixUnprotectedApi {

  private final HttpClient httpClient;
  private final JsonHandlers jsonHandlers;
  private final MatrixUri baseUri;

  private MatrixUnprotectedApi(
      HttpClientFactory httpClientFactory, JsonHandlers jsonHandlers, MatrixUris uris) {
    httpClient = httpClientFactory.build();
    this.jsonHandlers = requireNonNull(jsonHandlers);
    baseUri = uris.fetchBaseUri(httpClient, jsonHandlers);
  }

  public static MatrixUnprotectedApi load(
      HttpClientFactory httpClientFactory, JsonHandlers jsonHandlers, MatrixUris uris) {
    return new MatrixUnprotectedApi(httpClientFactory, jsonHandlers, uris);
  }

  public LoginOutput login(LoginInput loginInput) {
    HttpRequest loginRequest =
        HttpRequest.newBuilder(baseUri.addPathSegments("login").toUri())
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(jsonHandlers.publisher(loginInput))
            .build();

    try {
      return httpClient.send(loginRequest, jsonHandlers.handler(LoginOutput.class)).body().parse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }
}
