package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
class JsonBody<T> {

  private final int responseStatusCode;
  private final T body;

  public JsonBody(int responseStatusCode, T body) {
    this.responseStatusCode = responseStatusCode;
    this.body = body;
  }

  public boolean isNotFound() {
    return responseStatusCode == 404;
  }

  public T parse() {
    if (responseStatusCode != 200) {
      throw new IllegalStateException(
          String.format("Response has status code %s instead of 200", responseStatusCode));
    }
    return body;
  }
}
