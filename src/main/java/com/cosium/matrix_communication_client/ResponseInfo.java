package com.cosium.matrix_communication_client;

import java.net.http.HttpResponse;

/**
 * @author Réda Housni Alaoui
 */
public class ResponseInfo {

  private final HttpResponse.ResponseInfo responseInfo;

  public ResponseInfo(HttpResponse.ResponseInfo responseInfo) {
    this.responseInfo = responseInfo;
  }

  public static HttpResponse.BodyHandler<ResponseInfo> handler() {
    return responseInfo -> HttpResponse.BodySubscribers.replacing(new ResponseInfo(responseInfo));
  }

  public ResponseInfo assertSuccess() {
    int statusCode = responseInfo.statusCode();
    if (statusCode >= 200 && statusCode < 400) {
      return this;
    }
    throw new IllegalStateException(String.format("Response failed with code %s", statusCode));
  }
}
