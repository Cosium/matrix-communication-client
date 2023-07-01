package com.cosium.matrix_communication_client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.tuple;

import com.cosium.synapse_junit_extension.EnableSynapse;
import com.cosium.synapse_junit_extension.Synapse;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Réda Housni Alaoui
 */
@EnableSynapse
class RoomResourceTest {

  private MatrixResources resources;

  @BeforeEach
  void beforeEach(Synapse synapse) {
    resources =
        MatrixResources.factory()
            .builder()
            .https(synapse.https())
            .hostname(synapse.hostname())
            .port(synapse.port())
            .usernamePassword(synapse.adminUsername(), synapse.adminPassword())
            .build();
  }

  @Test
  @DisplayName("Create room")
  void test1() {
    assertThatCode(this::createRoom).doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Send message to room")
  void test2() {
    Message message = Message.builder().body("body").formattedBody("formattedBody").build();
    Message fetchedMessage = createRoom().sendMessage(message).fetch().content(Message.class);
    assertThat(List.of(message))
        .extracting(Message::body, Message::format, Message::formattedBody, Message::type)
        .containsExactly(
            tuple(
                fetchedMessage.body(),
                fetchedMessage.format(),
                fetchedMessage.formattedBody(),
                fetchedMessage.type()));
  }

  private RoomResource createRoom() {
    CreateRoomInput createRoomInput =
        CreateRoomInput.builder()
            .name(UUID.randomUUID().toString())
            .roomAliasName(UUID.randomUUID().toString())
            .topic(UUID.randomUUID().toString())
            .build();
    return resources.rooms().create(createRoomInput);
  }
}
