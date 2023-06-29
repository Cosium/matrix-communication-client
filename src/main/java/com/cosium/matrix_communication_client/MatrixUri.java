package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

/**
 * @author Réda Housni Alaoui
 */
class MatrixUri {

  private final URI value;

  public MatrixUri(String value) {
    this(URI.create(value));
  }

  public MatrixUri(URI value) {
    this.value = requireNonNull(value);
  }

  public MatrixUri addPathSegments(String... pathSegment) {
    String genuinePath = value.getPath();
    if (genuinePath.endsWith("/")) {
      genuinePath = genuinePath.substring(0, genuinePath.length() - 1);
    }

    StringBuilder pathBuilder = new StringBuilder(genuinePath);

    Stream.of(pathSegment)
        .map(String::trim)
        .map(this::assertValidPathSegment)
        .map(segment -> String.format("/%s", segment))
        .forEach(pathBuilder::append);

    try {
      return new MatrixUri(
          new URI(
              value.getScheme(),
              value.getUserInfo(),
              value.getHost(),
              value.getPort(),
              pathBuilder.toString(),
              value.getQuery(),
              value.getFragment()));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private String assertValidPathSegment(String segment) {
    if (segment == null) {
      throw new IllegalArgumentException("The segment should not be null");
    }
    if (segment.isBlank()) {
      throw new IllegalArgumentException("The segment should not be blank");
    }
    if (segment.contains("/")) {
      throw new IllegalArgumentException(
          String.format("Segment '%s' should not contain '/'", segment));
    }
    return segment;
  }

  public URI toUri() {
    return value;
  }
}
