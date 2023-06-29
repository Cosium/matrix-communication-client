package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Réda Housni Alaoui
 */
class SimpleRoomResource implements RoomResource {

  private final Lazy<MatrixApi> api;
  private final ObjectMapper objectMapper;
  private final String id;

  public SimpleRoomResource(Lazy<MatrixApi> api, ObjectMapper objectMapper, String id) {
    this.api = requireNonNull(api);
    this.objectMapper = requireNonNull(objectMapper);
    this.id = requireNonNull(id);
  }

  @Override
  public ClientEventResource sendMessage(Message message) {
    CreatedEvent createdEvent = api.get().sendMessageToRoom(message, id);
    return new SimpleClientEventResource(api, objectMapper, id, createdEvent.id());
  }
}
