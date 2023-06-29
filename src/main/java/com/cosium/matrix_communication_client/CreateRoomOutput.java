package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Réda Housni Alaoui
 */
class CreateRoomOutput {

  private final String roomId;

  @JsonCreator
  CreateRoomOutput(@JsonProperty("room_id") String roomId) {
    this.roomId = roomId;
  }

  public String roomId() {
    return roomId;
  }
}
