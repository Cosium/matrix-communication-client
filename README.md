[![Build Status](https://github.com/Cosium/matrix-communication-client/actions/workflows/ci.yml/badge.svg)](https://github.com/Cosium/matrix-communication-client/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.cosium.matrix_communication_client/matrix-communication-client.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.cosium.matrix_communication_client%22%20AND%20a%3A%22matrix-communication-client%22)


# Matrix Communication Client

A [Matrix](https://matrix.org/) java client.

# Example

```java
public class Example {

  public static void main(String[] args) {
    MatrixResources matrix =
        MatrixResources.factory()
            .builder()
            .https()
            .hostname("matrix.example.org")
            .defaultPort()
            .usernamePassword("jdoe", "secret")
            .build();

    RoomResource room = matrix
        .rooms()
        .create(
            CreateRoomInput.builder()
                .name("Science")
                .roomAliasName("science")
                .topic("Anything about science")
                .build());
	
    room.sendMessage(Message.builder().body("Hello !").formattedBody("<b>Hello !</b>").build());
  }
}
```

Another example with existing room:

```java
public class Example {

  public static void main(String[] args) {
    MatrixResources matrix =
        MatrixResources.factory()
            .builder()
            .https()
            .hostname("matrix.example.org")
            .defaultPort()
            .usernamePassword("jdoe", "secret")
            .build();

    RoomResource room = matrix
        .rooms()
        .byId("!PVvauSmjcHLwoAJkyT:matrix.example.org");
	
    room.sendMessage(Message.builder().body("Hello !").formattedBody("<b>Hello !</b>").build());
  }
}
```

# Dependency

```xml
<dependency>
  <groupId>com.cosium.matrix_communication_client</groupId>
  <artifactId>matrix-communication-client</artifactId>
  <verion>${matrix-communication-client.version}</verion>
</dependency>
```
