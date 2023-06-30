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
            .https(false)
            .hostname("matrix.example.org")
            .usernamePassword("jdoe", "secret")
            .build();

	RoomResource room = matrix
        .rooms()
        .create(
            CreateRoomInput.builder()
                .name(UUID.randomUUID().toString())
                .roomAliasName(UUID.randomUUID().toString())
                .topic(UUID.randomUUID().toString())
                .build());
	
	room.sendMessage(Message.builder().body("body").formattedBody("formattedBody").build());
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
