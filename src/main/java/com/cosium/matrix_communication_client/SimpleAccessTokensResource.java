package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

/**
 * @author Réda Housni Alaoui
 */
class SimpleAccessTokensResource implements AccessTokensResource {

  private final AccessTokenFactory accessTokenFactory;

  SimpleAccessTokensResource(AccessTokenFactory accessTokenFactory) {
    this.accessTokenFactory = requireNonNull(accessTokenFactory);
  }

  @Override
  public String create() {
    return accessTokenFactory.build();
  }
}
