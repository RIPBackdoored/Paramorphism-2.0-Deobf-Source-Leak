package org.eclipse.aether.internal.impl;

import java.util.Arrays;
import org.eclipse.aether.repository.RemoteRepository;

final class DefaultRemoteRepositoryManager$LoggedMirror {
   private final Object[] keys;

   DefaultRemoteRepositoryManager$LoggedMirror(RemoteRepository var1, RemoteRepository var2) {
      super();
      this.keys = new Object[]{var2.getId(), var2.getUrl(), var1.getId(), var1.getUrl()};
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DefaultRemoteRepositoryManager$LoggedMirror)) {
         return false;
      } else {
         DefaultRemoteRepositoryManager$LoggedMirror var2 = (DefaultRemoteRepositoryManager$LoggedMirror)var1;
         return Arrays.equals(this.keys, var2.keys);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.keys);
   }
}
