package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Réda Housni Alaoui
 */
class SimpleMatrixResources implements MatrixResources {

  private final ObjectMapper objectMapper;
  private final AccessTokenFactory accessTokenFactory;
  private final Lazy<MatrixApi> api;

  public SimpleMatrixResources(
      boolean https,
      String hostname,
      Integer port,
      AccessTokenFactoryFactory accessTokenFactoryFactory) {
    objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    MatrixUris matrixUris = new MatrixUris(https, new MatrixHostname(hostname), port);
    JsonHandlers jsonHandlers = new JsonHandlers(objectMapper);
    accessTokenFactory = accessTokenFactoryFactory.build(jsonHandlers, matrixUris);
    api = Lazy.of(() -> MatrixApi.load(jsonHandlers, matrixUris, accessTokenFactory));
  }

  @Override
  public AccessTokensResource accessTokens() {
    return new SimpleAccessTokensResource(accessTokenFactory);
  }

  @Override
  public RoomsResource rooms() {
    return new SimpleRoomsResource(api, objectMapper);
  }
}
