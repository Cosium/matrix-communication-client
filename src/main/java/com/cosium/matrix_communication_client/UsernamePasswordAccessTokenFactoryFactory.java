package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

/**
 * @author Réda Housni Alaoui
 */
class UsernamePasswordAccessTokenFactoryFactory implements AccessTokenFactoryFactory {

  private final String username;
  private final String password;

  UsernamePasswordAccessTokenFactoryFactory(String username, String password) {
    this.username = requireNonNull(username);
    this.password = requireNonNull(password);
  }

  @Override
  public AccessTokenFactory build(
      HttpClientFactory httpClientFactory, JsonHandlers jsonHandlers, MatrixUris uris) {
    return new UsernamePasswordAccessTokenFactory(
        Lazy.of(() -> MatrixUnprotectedApi.load(httpClientFactory, jsonHandlers, uris)),
        username,
        password);
  }
}
