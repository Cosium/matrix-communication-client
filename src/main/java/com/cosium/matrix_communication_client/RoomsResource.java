package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
public interface RoomsResource {

  RoomResource byId(String id);

  RoomResource create(CreateRoomInput input);
}
