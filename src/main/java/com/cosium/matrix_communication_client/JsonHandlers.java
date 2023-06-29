package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import com.cosium.matrix_communication_client.internal.JsonBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author Réda Housni Alaoui
 */
class JsonHandlers {

  private final ObjectMapper objectMapper;

  public JsonHandlers(ObjectMapper objectMapper) {
    this.objectMapper = requireNonNull(objectMapper);
  }

  public HttpRequest.BodyPublisher publisher(Object value) {
    try {
      String json = objectMapper.writeValueAsString(value);
      return HttpRequest.BodyPublishers.ofString(json);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  public <T> HttpResponse.BodyHandler<JsonBody<T>> handler(Class<T> type) {
    return responseInfo -> createBodySubscriber(responseInfo, type);
  }

  private <T> HttpResponse.BodySubscriber<JsonBody<T>> createBodySubscriber(
      HttpResponse.ResponseInfo responseInfo, Class<T> type) {
    int statusCode = responseInfo.statusCode();
    if (statusCode != 200) {
      return HttpResponse.BodySubscribers.replacing(new JsonBody<>(statusCode, null));
    }

    return HttpResponse.BodySubscribers.mapping(
        HttpResponse.BodySubscribers.ofInputStream(),
        inputStream -> {
          try {
            return new JsonBody<>(statusCode, objectMapper.readValue(inputStream, type));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }
}
