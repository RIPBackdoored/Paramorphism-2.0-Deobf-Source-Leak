package org.eclipse.aether;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DefaultRepositoryCache implements RepositoryCache {
   private final Map cache = new ConcurrentHashMap(256);

   public DefaultRepositoryCache() {
      super();
   }

   public Object get(RepositorySystemSession var1, Object var2) {
      return this.cache.get(var2);
   }

   public void put(RepositorySystemSession var1, Object var2, Object var3) {
      if (var3 != null) {
         this.cache.put(var2, var3);
      } else {
         this.cache.remove(var2);
      }

   }
}
