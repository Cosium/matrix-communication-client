package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
class UsernamePasswordAccessTokenFactory implements AccessTokenFactory {

  private final Lazy<String> accessToken;

  public UsernamePasswordAccessTokenFactory(
      Lazy<MatrixUnprotectedApi> api, String username, String password) {
    accessToken =
        Lazy.of(
            () ->
                api.get()
                    .login(
                        new LoginInput(
                            "m.login.password",
                            new LoginInput.Identifier("m.id.user", username),
                            password))
                    .accessToken());
  }

  @Override
  public String build() {
    return accessToken.get();
  }
}
