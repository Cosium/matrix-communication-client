package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Réda Housni Alaoui
 */
class CreatedEvent {

  private final String id;

  public CreatedEvent(@JsonProperty("event_id") String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }
}
