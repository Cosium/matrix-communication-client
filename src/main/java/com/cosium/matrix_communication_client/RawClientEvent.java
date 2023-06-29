package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Réda Housni Alaoui
 */
class RawClientEvent {

  private final ObjectNode content;
  private final String id;
  private final long originServerTs;
  private final String roomId;
  private final String sender;
  private final String stateKey;
  private final String type;
  private final UnsignedData unsigned;

  @JsonCreator
  RawClientEvent(
      @JsonProperty("content") ObjectNode content,
      @JsonProperty("event_id") String id,
      @JsonProperty("origin_server_ts") long originServerTs,
      @JsonProperty("room_id") String roomId,
      @JsonProperty("sender") String sender,
      @JsonProperty("state_key") String stateKey,
      @JsonProperty("type") String type,
      @JsonProperty("unsigned") UnsignedData unsigned) {
    this.content = requireNonNull(content);
    this.id = requireNonNull(id);
    this.originServerTs = originServerTs;
    this.roomId = requireNonNull(roomId);
    this.sender = requireNonNull(sender);
    this.stateKey = stateKey;
    this.type = requireNonNull(type);
    this.unsigned = unsigned;
  }

  public ObjectNode content() {
    return content;
  }

  public String id() {
    return id;
  }

  public long originServerTs() {
    return originServerTs;
  }

  public String roomId() {
    return roomId;
  }

  public String sender() {
    return sender;
  }

  public String stateKey() {
    return stateKey;
  }

  public String type() {
    return type;
  }

  public UnsignedData unsigned() {
    return unsigned;
  }

  public static class UnsignedData {
    private final Long age;
    private final ObjectNode previousContent;
    private final RawClientEvent redactedBecause;
    private final String transactionId;

    public UnsignedData(
        @JsonProperty("age") Long age,
        @JsonProperty("prev_content") ObjectNode previousContent,
        @JsonProperty("redacted_because") RawClientEvent redactedBecause,
        @JsonProperty("transaction_id") String transactionId) {
      this.age = age;
      this.previousContent = previousContent;
      this.redactedBecause = redactedBecause;
      this.transactionId = transactionId;
    }

    public Long age() {
      return age;
    }

    public ObjectNode previousContent() {
      return previousContent;
    }

    public RawClientEvent redactedBecause() {
      return redactedBecause;
    }

    public String transactionId() {
      return transactionId;
    }
  }
}
