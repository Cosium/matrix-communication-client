package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;

/**
 * @author Réda Housni Alaoui
 */
class RawClientEventPage {

  private final List<RawClientEvent> chunk;
  private final String end;
  private final String start;
  private final List<RawClientEvent> state;

  @JsonCreator
  RawClientEventPage(
      @JsonProperty("chunk") List<RawClientEvent> chunk,
      @JsonProperty("end") String end,
      @JsonProperty("start") String start,
      @JsonProperty("state") List<RawClientEvent> state) {
    this.chunk = Optional.ofNullable(chunk).map(List::copyOf).orElseGet(List::of);
    this.end = end;
    this.start = start;
    this.state = Optional.ofNullable(state).map(List::copyOf).orElseGet(List::of);
  }

  public List<RawClientEvent> chunk() {
    return chunk;
  }

  public String end() {
    return end;
  }

  public String start() {
    return start;
  }

  public List<RawClientEvent> state() {
    return state;
  }
}
