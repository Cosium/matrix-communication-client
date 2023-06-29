package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * @author Réda Housni Alaoui
 */
class MatrixHostname {

  private final String value;

  public MatrixHostname(String value) {
    this.value = requireNonNull(value);
  }

  public MatrixUri createUri(boolean https, Integer port, String... pathSegments) {
    StringBuilder uriBuilder = new StringBuilder();
    if (https) {
      uriBuilder.append("https://");
    } else {
      uriBuilder.append("http://");
    }
    uriBuilder.append(value);
    Optional.ofNullable(port).ifPresent(aPort -> uriBuilder.append(":").append(aPort));

    return new MatrixUri(uriBuilder.toString()).addPathSegments(pathSegments);
  }
}
