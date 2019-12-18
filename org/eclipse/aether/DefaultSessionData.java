package org.eclipse.aether;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class DefaultSessionData implements SessionData {
   private final ConcurrentMap data = new ConcurrentHashMap();

   public DefaultSessionData() {
      super();
   }

   public void set(Object var1, Object var2) {
      Objects.requireNonNull(var1, "key cannot be null");
      if (var2 != null) {
         this.data.put(var1, var2);
      } else {
         this.data.remove(var1);
      }

   }

   public boolean set(Object var1, Object var2, Object var3) {
      Objects.requireNonNull(var1, "key cannot be null");
      if (var3 != null) {
         if (var2 == null) {
            return this.data.putIfAbsent(var1, var3) == null;
         } else {
            return this.data.replace(var1, var2, var3);
         }
      } else if (var2 == null) {
         return !this.data.containsKey(var1);
      } else {
         return this.data.remove(var1, var2);
      }
   }

   public Object get(Object var1) {
      Objects.requireNonNull(var1, "key cannot be null");
      return this.data.get(var1);
   }
}
