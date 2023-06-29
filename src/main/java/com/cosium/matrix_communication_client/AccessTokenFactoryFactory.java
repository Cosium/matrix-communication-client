package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
interface AccessTokenFactoryFactory {

  AccessTokenFactory build(JsonHandlers jsonHandlers, MatrixUris uris);
}
