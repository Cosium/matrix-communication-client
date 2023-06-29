package com.cosium.matrix_communication_client.test_infrastructure;

import static java.util.Objects.requireNonNull;

import org.junit.jupiter.api.extension.ExtensionContext;

public class JUnitCloseableResource implements ExtensionContext.Store.CloseableResource {

  private final Runnable close;

  public JUnitCloseableResource(Runnable close) {
    this.close = requireNonNull(close);
  }

  @Override
  public void close() {
    close.run();
  }
}
