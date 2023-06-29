package com.cosium.matrix_communication_client;

/**
 * @author Réda Housni Alaoui
 */
class UsernamePassordAccessTokenFactory implements AccessTokenFactory {

  private final Lazy<String> accessToken;

  public UsernamePassordAccessTokenFactory(
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
