package org.eclipse.aether.util.repository;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.AuthenticationSelector;
import org.eclipse.aether.repository.RemoteRepository;

public final class DefaultAuthenticationSelector implements AuthenticationSelector {
   private final Map repos = new HashMap();

   public DefaultAuthenticationSelector() {
      super();
   }

   public DefaultAuthenticationSelector add(String var1, Authentication var2) {
      if (var2 != null) {
         this.repos.put(var1, var2);
      } else {
         this.repos.remove(var1);
      }

      return this;
   }

   public Authentication getAuthentication(RemoteRepository var1) {
      return (Authentication)this.repos.get(var1.getId());
   }
}
