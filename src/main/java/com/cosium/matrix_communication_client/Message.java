package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * @author Réda Housni Alaoui
 */
public class Message {

  private final String body;
  private final String format;
  private final String formattedBody;
  private final String type;

  private Message(Builder builder) {
    body = builder.body;
    format = builder.format;
    formattedBody = builder.formattedBody;
    type = builder.type;
  }

  @JsonCreator
  Message(
      @JsonProperty("body") String body,
      @JsonProperty("format") String format,
      @JsonProperty("formatted_body") String formattedBody,
      @JsonProperty("msgtype") String type) {
    this.body = body;
    this.format = format;
    this.formattedBody = formattedBody;
    this.type = type;
  }

  public static Builder builder() {
    return new Builder();
  }

  @JsonProperty("body")
  public String body() {
    return body;
  }

  @JsonProperty("format")
  public String format() {
    return format;
  }

  @JsonProperty("formatted_body")
  public String formattedBody() {
    return formattedBody;
  }

  @JsonProperty("msgtype")
  public String type() {
    return type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(body, format, formattedBody, type);
  }

  public static final class Builder {
    private String body;
    private String format = "org.matrix.custom.html";
    private String formattedBody;
    private String type = "m.text";

    private Builder() {}

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public Builder format(String format) {
      this.format = format;
      return this;
    }

    public Builder formattedBody(String formattedBody) {
      this.formattedBody = formattedBody;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Message build() {
      return new Message(this);
    }
  }
}
