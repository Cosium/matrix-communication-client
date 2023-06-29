package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import java.net.http.HttpClient;

/**
 * @author Réda Housni Alaoui
 */
class MatrixUris {

  private final boolean https;
  private final MatrixHostname hostname;
  private final Integer port;

  public MatrixUris(boolean https, MatrixHostname hostname, Integer port) {
    this.https = https;
    this.hostname = requireNonNull(hostname);
    this.port = port;
  }

  public MatrixUri create(String... paths) {
    return hostname.createUri(https, port, paths);
  }

  public MatrixUri fetchBaseUri(HttpClient httpClient, JsonHandlers jsonHandlers) {
    return WellKnownMatrixClient.fetch(httpClient, jsonHandlers, this)
        .map(WellKnownMatrixClient::homeServer)
        .map(WellKnownMatrixClient.HomeServer::baseUri)
        .orElseGet(this::create)
        .addPathSegments("_matrix", "client", "v3");
  }
}
