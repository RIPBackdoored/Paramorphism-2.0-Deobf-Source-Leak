package org.eclipse.aether.util.repository;

import java.util.Objects;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.RemoteRepository;

public final class ConservativeProxySelector implements ProxySelector {
   private final ProxySelector selector;

   public ConservativeProxySelector(ProxySelector var1) {
      super();
      this.selector = (ProxySelector)Objects.requireNonNull(var1, "proxy selector cannot be null");
   }

   public Proxy getProxy(RemoteRepository var1) {
      Proxy var2 = var1.getProxy();
      return var2 != null ? var2 : this.selector.getProxy(var1);
   }
}
