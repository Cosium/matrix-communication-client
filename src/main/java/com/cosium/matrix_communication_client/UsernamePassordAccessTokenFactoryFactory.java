package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

/**
 * @author Réda Housni Alaoui
 */
class UsernamePassordAccessTokenFactoryFactory implements AccessTokenFactoryFactory {

  private final String username;
  private final String password;

  UsernamePassordAccessTokenFactoryFactory(String username, String password) {
    this.username = requireNonNull(username);
    this.password = requireNonNull(password);
  }

  @Override
  public AccessTokenFactory build(JsonHandlers jsonHandlers, MatrixUris uris) {
    return new UsernamePassordAccessTokenFactory(
        Lazy.of(() -> MatrixUnprotectedApi.load(jsonHandlers, uris)), username, password);
  }
}
