package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Réda Housni Alaoui
 */
public class ClientEvent {

  private final ObjectMapper objectMapper;
  private final RawClientEvent raw;

  public ClientEvent(ObjectMapper objectMapper, RawClientEvent raw) {
    this.objectMapper = requireNonNull(objectMapper);
    this.raw = requireNonNull(raw);
  }

  public <T> T content(Class<T> contentType) {
    return objectMapper.convertValue(raw.content(), contentType);
  }

  public String id() {
    return raw.id();
  }

  public long originServerTs() {
    return raw.originServerTs();
  }

  public String roomId() {
    return raw.roomId();
  }

  public String sender() {
    return raw.sender();
  }

  public String stateKey() {
    return raw.stateKey();
  }

  public String type() {
    return raw.type();
  }

  public UnsignedData unsigned() {
    return new UnsignedData(objectMapper, raw.unsigned());
  }

  public static class UnsignedData {
    private final ObjectMapper objectMapper;
    private final RawClientEvent.UnsignedData raw;

    public UnsignedData(ObjectMapper objectMapper, RawClientEvent.UnsignedData raw) {
      this.objectMapper = requireNonNull(objectMapper);
      this.raw = requireNonNull(raw);
    }

    public Long age() {
      return raw.age();
    }

    public <T> T previousContent(Class<T> contentType) {
      return objectMapper.convertValue(raw.previousContent(), contentType);
    }

    public ClientEvent redactedBecause() {
      return new ClientEvent(objectMapper, raw.redactedBecause());
    }

    public String transactionId() {
      return raw.transactionId();
    }
  }
}
