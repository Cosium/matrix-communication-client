package com.cosium.matrix_communication_client;

import static java.util.Objects.requireNonNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

/**
 * @author Réda Housni Alaoui
 */
class MatrixUri {

  private final URI uriValue;

  public MatrixUri(String value) {
    this(URI.create(value));
  }

  public MatrixUri(URI value) {
    this.uriValue = requireNonNull(value);
  }

  public MatrixUri addPathSegments(String... pathSegment) {
    String genuinePath = uriValue.getPath();
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
              uriValue.getScheme(),
              uriValue.getUserInfo(),
              uriValue.getHost(),
              uriValue.getPort(),
              pathBuilder.toString(),
              uriValue.getQuery(),
              uriValue.getFragment()));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public MatrixUri addQueryParameter(String name, String value) {
    if (value == null) {
      return this;
    }

    String genuineQuery = uriValue.getQuery();
    StringBuilder newQuery;
    if (genuineQuery != null && !genuineQuery.isBlank()) {
      newQuery = new StringBuilder(uriValue.getQuery()).append("&");
    } else {
      newQuery = new StringBuilder();
    }
    newQuery.append(name).append("=").append(value);

    try {
      return new MatrixUri(
          new URI(
              uriValue.getScheme(),
              uriValue.getUserInfo(),
              uriValue.getHost(),
              uriValue.getPort(),
              uriValue.getPath(),
              newQuery.toString(),
              uriValue.getFragment()));
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
    return uriValue;
  }
}
