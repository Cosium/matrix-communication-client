package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Réda Housni Alaoui
 */
public class CreateRoomInput {

  private final CreationContent creationContent;
  private final String name;
  private final String preset;
  private final String roomAliasName;
  private final String topic;

  private CreateRoomInput(Builder builder) {
    creationContent = builder.creationContent;
    name = builder.name;
    preset = builder.preset;
    roomAliasName = builder.roomAliasName;
    topic = builder.topic;
  }

  public static Builder builder() {
    return new Builder();
  }

  @JsonProperty("creation_content")
  public CreationContent creationContent() {
    return creationContent;
  }

  @JsonProperty("name")
  public String name() {
    return name;
  }

  @JsonProperty("preset")
  public String preset() {
    return preset;
  }

  @JsonProperty("room_alias_name")
  public String roomAliasName() {
    return roomAliasName;
  }

  @JsonProperty("topic")
  public String topic() {
    return topic;
  }

  public static class CreationContent {
    private final boolean federate;

    public CreationContent(boolean federate) {
      this.federate = federate;
    }

    @JsonProperty("m.federate")
    public boolean federate() {
      return federate;
    }
  }

  public static class Builder {

    private CreationContent creationContent = new CreationContent(false);
    private String name;
    private String preset = "public_chat";
    private String roomAliasName;
    private String topic;

    private Builder() {}

    public Builder creationContent(CreationContent creationContent) {
      this.creationContent = creationContent;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder preset(String preset) {
      this.preset = preset;
      return this;
    }

    public Builder roomAliasName(String roomAliasName) {
      this.roomAliasName = roomAliasName;
      return this;
    }

    public Builder topic(String topic) {
      this.topic = topic;
      return this;
    }

    public CreateRoomInput build() {
      return new CreateRoomInput(this);
    }
  }
}
