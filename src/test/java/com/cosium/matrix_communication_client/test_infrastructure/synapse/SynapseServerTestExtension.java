package com.cosium.matrix_communication_client.test_infrastructure.synapse;

import com.cosium.matrix_communication_client.test_infrastructure.CloseableResource;
import com.cosium.matrix_communication_client.test_infrastructure.JUnitCloseableResource;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

class SynapseServerTestExtension implements BeforeAllCallback, ParameterResolver {

  @Override
  public void beforeAll(ExtensionContext context) {
    ExtensionContext.Store store = context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL);
    if (store.get(SynapseServer.class) != null) {
      return;
    }
    CloseableResource<SynapseServer> server = SynapseServer.start();
    store.put(SynapseServer.class + "#close", new JUnitCloseableResource(server::close));
    store.put(SynapseServer.class, server.resource());
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return SynapseServer.class.equals(parameterContext.getParameter().getType());
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {

    return extensionContext
        .getRoot()
        .getStore(ExtensionContext.Namespace.GLOBAL)
        .get(SynapseServer.class, SynapseServer.class);
  }
}
