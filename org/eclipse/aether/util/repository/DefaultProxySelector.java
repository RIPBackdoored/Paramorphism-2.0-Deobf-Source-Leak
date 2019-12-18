package org.eclipse.aether.util.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.RemoteRepository;

public final class DefaultProxySelector implements ProxySelector {
   private List proxies = new ArrayList();

   public DefaultProxySelector() {
      super();
   }

   public DefaultProxySelector add(Proxy var1, String var2) {
      Objects.requireNonNull(var1, "proxy cannot be null");
      this.proxies.add(new DefaultProxySelector$ProxyDef(var1, var2));
      return this;
   }

   public Proxy getProxy(RemoteRepository var1) {
      HashMap var2 = new HashMap();
      String var3 = var1.getHost();
      Iterator var4 = this.proxies.iterator();

      DefaultProxySelector$ProxyDef var5;
      while(var4.hasNext()) {
         var5 = (DefaultProxySelector$ProxyDef)var4.next();
         if (!var5.nonProxyHosts.isNonProxyHost(var3)) {
            String var6 = var5.proxy.getType().toLowerCase(Locale.ENGLISH);
            if (!var2.containsKey(var6)) {
               var2.put(var6, var5);
            }
         }
      }

      String var7 = var1.getProtocol().toLowerCase(Locale.ENGLISH);
      if ("davs".equals(var7)) {
         var7 = "https";
      } else if ("dav".equals(var7)) {
         var7 = "http";
      } else if (var7.startsWith("dav:")) {
         var7 = var7.substring("dav:".length());
      }

      var5 = (DefaultProxySelector$ProxyDef)var2.get(var7);
      if (var5 == null && "https".equals(var7)) {
         var5 = (DefaultProxySelector$ProxyDef)var2.get("http");
      }

      return var5 != null ? var5.proxy : null;
   }
}
