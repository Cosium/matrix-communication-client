package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Réda Housni Alaoui
 */
class SimpleClientEventResource implements ClientEventResource {

  private final Lazy<MatrixApi> api;
  private final ObjectMapper objectMapper;
  private final String roomId;
  private final String id;

  public SimpleClientEventResource(
      Lazy<MatrixApi> api, ObjectMapper objectMapper, String roomId, String id) {
    this.api = requireNonNull(api);
    this.objectMapper = requireNonNull(objectMapper);
    this.roomId = requireNonNull(roomId);
    this.id = requireNonNull(id);
  }

  @Override
  public ClientEvent fetch() {
    return new ClientEvent(objectMapper, api.get().fetchEvent(roomId, id));
  }
}
