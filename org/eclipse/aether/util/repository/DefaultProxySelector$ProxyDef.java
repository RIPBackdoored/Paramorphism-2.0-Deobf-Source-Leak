package org.eclipse.aether.util.repository;

import org.eclipse.aether.repository.Proxy;

class DefaultProxySelector$ProxyDef {
   final Proxy proxy;
   final DefaultProxySelector$NonProxyHosts nonProxyHosts;

   DefaultProxySelector$ProxyDef(Proxy var1, String var2) {
      super();
      this.proxy = var1;
      this.nonProxyHosts = new DefaultProxySelector$NonProxyHosts(var2);
   }
}
