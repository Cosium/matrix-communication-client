package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
public interface MatrixResources {

  static MatrixResourcesFactory factory() {
    return new MatrixResourcesFactory();
  }

  AccessTokensResource accessTokens();

  RoomsResource rooms();
}
