package org.jline.style;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public class MemoryStyleSource implements StyleSource {
   private static final Logger log = Logger.getLogger(MemoryStyleSource.class.getName());
   private final Map backing = new ConcurrentHashMap();

   public MemoryStyleSource() {
      super();
   }

   @Nullable
   public String get(String var1, String var2) {
      String var3 = null;
      Map var4 = (Map)this.backing.get(var1);
      if (var4 != null) {
         var3 = (String)var4.get(var2);
      }

      if (log.isLoggable(Level.FINEST)) {
         log.finest(String.format("Get: [%s] %s -> %s", var1, var2, var3));
      }

      return var3;
   }

   public void set(String var1, String var2, String var3) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      Objects.requireNonNull(var3);
      ((Map)this.backing.computeIfAbsent(var1, MemoryStyleSource::lambda$set$0)).put(var2, var3);
      if (log.isLoggable(Level.FINEST)) {
         log.finest(String.format("Set: [%s] %s -> %s", var1, var2, var3));
      }

   }

   public void remove(String var1) {
      Objects.requireNonNull(var1);
      if (this.backing.remove(var1) != null && log.isLoggable(Level.FINEST)) {
         log.finest(String.format("Removed: [%s]", var1));
      }

   }

   public void remove(String var1, String var2) {
      Objects.requireNonNull(var1);
      Objects.requireNonNull(var2);
      Map var3 = (Map)this.backing.get(var1);
      if (var3 != null) {
         var3.remove(var2);
         if (log.isLoggable(Level.FINEST)) {
            log.finest(String.format("Removed: [%s] %s", var1, var2));
         }
      }

   }

   public void clear() {
      this.backing.clear();
      log.finest("Cleared");
   }

   public Iterable groups() {
      return Collections.unmodifiableSet(this.backing.keySet());
   }

   public Map styles(String var1) {
      Objects.requireNonNull(var1);
      Map var2 = (Map)this.backing.get(var1);
      if (var2 == null) {
         var2 = Collections.emptyMap();
      }

      return Collections.unmodifiableMap(var2);
   }

   private static Map lambda$set$0(String var0) {
      return new ConcurrentHashMap();
   }
}
