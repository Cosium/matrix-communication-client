package com.cosium.matrix_communication_client;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Optional;

/**
 * @author Réda Housni Alaoui
 */
class HttpClientFactory {

  private final Duration connectTimeout;

  HttpClientFactory(Duration connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public HttpClient build() {
    HttpClient.Builder builder = HttpClient.newBuilder();
    Optional.ofNullable(connectTimeout).ifPresent(builder::connectTimeout);
    return builder.followRedirects(HttpClient.Redirect.NORMAL).build();
  }
}
