package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
public interface RoomResource {

  /**
   * <a
   * href="https://spec.matrix.org/latest/client-server-api/#mroommessage">https://spec.matrix.org/latest/client-server-api/#mroommessage</a>
   */
  ClientEventResource sendMessage(Message messageInput);
}
