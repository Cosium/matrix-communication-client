package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
interface AccessTokenFactoryFactory {

  AccessTokenFactory build(
      HttpClientFactory httpClientFactory, JsonHandlers jsonHandlers, MatrixUris uris);
}
