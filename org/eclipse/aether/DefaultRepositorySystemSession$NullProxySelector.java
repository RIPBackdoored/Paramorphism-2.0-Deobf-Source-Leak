package org.eclipse.aether;

import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.RemoteRepository;

class DefaultRepositorySystemSession$NullProxySelector implements ProxySelector {
   public static final ProxySelector INSTANCE = new DefaultRepositorySystemSession$NullProxySelector();

   DefaultRepositorySystemSession$NullProxySelector() {
      super();
   }

   public Proxy getProxy(RemoteRepository var1) {
      return var1.getProxy();
   }
}
