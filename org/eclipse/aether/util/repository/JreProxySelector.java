package org.eclipse.aether.util.repository;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.Proxy.Type;
import java.util.Iterator;
import java.util.List;
import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.RemoteRepository;

public final class JreProxySelector implements ProxySelector {
   public JreProxySelector() {
      super();
   }

   public Proxy getProxy(RemoteRepository var1) {
      List var2 = null;

      try {
         URI var3 = (new URI(var1.getUrl())).parseServerAuthority();
         var2 = java.net.ProxySelector.getDefault().select(var3);
      } catch (Exception var6) {
      }

      if (var2 != null) {
         Iterator var7 = var2.iterator();

         while(var7.hasNext()) {
            java.net.Proxy var4 = (java.net.Proxy)var7.next();
            if (Type.DIRECT.equals(var4.type())) {
               break;
            }

            if (Type.HTTP.equals(var4.type()) && isValid(var4.address())) {
               InetSocketAddress var5 = (InetSocketAddress)var4.address();
               return new Proxy("http", var5.getHostName(), var5.getPort(), JreProxySelector$JreProxyAuthentication.INSTANCE);
            }
         }
      }

      return null;
   }

   private static boolean isValid(SocketAddress var0) {
      if (var0 instanceof InetSocketAddress) {
         InetSocketAddress var1 = (InetSocketAddress)var0;
         if (var1.getPort() <= 0) {
            return false;
         } else {
            return var1.getHostName() != null && var1.getHostName().length() > 0;
         }
      } else {
         return false;
      }
   }
}
