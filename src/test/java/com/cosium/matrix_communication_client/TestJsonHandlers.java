package com.cosium.matrix_communication_client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Réda Housni Alaoui
 */
public class TestJsonHandlers extends JsonHandlers {

  public static final TestJsonHandlers INSTANCE = new TestJsonHandlers();

  private TestJsonHandlers() {
    super(new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
  }
}
