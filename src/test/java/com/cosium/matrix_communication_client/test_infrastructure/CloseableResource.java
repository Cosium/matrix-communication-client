package com.cosium.matrix_communication_client.test_infrastructure;

/**
 * @author Réda Housni Alaoui
 */
public interface CloseableResource<T> {

  T resource();

  void close();
}
