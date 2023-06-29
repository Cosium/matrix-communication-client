package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Réda Housni Alaoui
 */
class LoginOutput {

  private final String accessToken;

  @JsonCreator
  LoginOutput(@JsonProperty("access_token") String accessToken) {
    this.accessToken = accessToken;
  }

  public String accessToken() {
    return accessToken;
  }
}
