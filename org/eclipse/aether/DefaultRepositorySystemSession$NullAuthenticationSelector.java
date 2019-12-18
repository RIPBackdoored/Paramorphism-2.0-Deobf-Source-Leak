package org.eclipse.aether;

import org.eclipse.aether.repository.Authentication;
import org.eclipse.aether.repository.AuthenticationSelector;
import org.eclipse.aether.repository.RemoteRepository;

class DefaultRepositorySystemSession$NullAuthenticationSelector implements AuthenticationSelector {
   public static final AuthenticationSelector INSTANCE = new DefaultRepositorySystemSession$NullAuthenticationSelector();

   DefaultRepositorySystemSession$NullAuthenticationSelector() {
      super();
   }

   public Authentication getAuthentication(RemoteRepository var1) {
      return var1.getAuthentication();
   }
}
