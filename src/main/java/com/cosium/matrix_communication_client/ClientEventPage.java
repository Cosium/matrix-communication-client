package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Réda Housni Alaoui
 */
public class ClientEventPage {

  private final ObjectMapper objectMapper;
  private final RawClientEventPage raw;

  ClientEventPage(ObjectMapper objectMapper, RawClientEventPage raw) {
    this.objectMapper = requireNonNull(objectMapper);
    this.raw = requireNonNull(raw);
  }

  public List<ClientEvent> chunk() {
    return raw.chunk().stream()
        .map(rawClientEvent -> new ClientEvent(objectMapper, rawClientEvent))
        .collect(Collectors.toUnmodifiableList());
  }

  public String end() {
    return raw.end();
  }

  public String start() {
    return raw.start();
  }

  public List<ClientEvent> state() {
    return raw.state().stream()
        .map(rawClientEvent -> new ClientEvent(objectMapper, rawClientEvent))
        .collect(Collectors.toUnmodifiableList());
  }
}
