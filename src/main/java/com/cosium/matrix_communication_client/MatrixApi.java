package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.UUID;

/**
 * @author Réda Housni Alaoui
 */
class MatrixApi {

  private static final String APPLICATION_JSON = "application/json";
  private final HttpClient httpClient;
  private final JsonHandlers jsonHandlers;
  private final MatrixUri baseUri;
  private final AccessTokenFactory accessTokenFactory;

  private MatrixApi(
      JsonHandlers jsonHandlers, MatrixUris uris, AccessTokenFactory accessTokenFactory) {
    httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
    this.jsonHandlers = requireNonNull(jsonHandlers);
    baseUri = uris.fetchBaseUri(httpClient, jsonHandlers);
    this.accessTokenFactory = requireNonNull(accessTokenFactory);
  }

  public static MatrixApi load(
      JsonHandlers jsonHandlers, MatrixUris uris, AccessTokenFactory accessTokenFactory) {
    return new MatrixApi(jsonHandlers, uris, accessTokenFactory);
  }

  public CreatedEvent sendMessageToRoom(Message input, String roomId) {

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(
                baseUri
                    .addPathSegments(
                        "rooms", roomId, "send", "m.room.message", UUID.randomUUID().toString())
                    .toUri())
            .header("Authorization", String.format("Bearer %s", accessTokenFactory.build()))
            .header("Content-Type", APPLICATION_JSON)
            .header("Accept", APPLICATION_JSON)
            .PUT(jsonHandlers.publisher(input))
            .build();

    try {
      return httpClient.send(request, jsonHandlers.handler(CreatedEvent.class)).body().parse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  public CreateRoomOutput createRoom(CreateRoomInput input) {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(baseUri.addPathSegments("createRoom").toUri())
            .header("Authorization", String.format("Bearer %s", accessTokenFactory.build()))
            .header("Content-Type", APPLICATION_JSON)
            .header("Accept", APPLICATION_JSON)
            .POST(jsonHandlers.publisher(input))
            .build();

    try {
      return httpClient.send(request, jsonHandlers.handler(CreateRoomOutput.class)).body().parse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  public RawClientEvent fetchEvent(String roomId, String eventId) {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(baseUri.addPathSegments("rooms", roomId, "event", eventId).toUri())
            .header("Authorization", String.format("Bearer %s", accessTokenFactory.build()))
            .header("Accept", APPLICATION_JSON)
            .GET()
            .build();

    try {
      return httpClient.send(request, jsonHandlers.handler(RawClientEvent.class)).body().parse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }
}
