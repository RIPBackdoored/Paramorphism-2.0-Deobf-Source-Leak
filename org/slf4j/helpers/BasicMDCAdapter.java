package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.spi.MDCAdapter;

public class BasicMDCAdapter implements MDCAdapter {
   private InheritableThreadLocal inheritableThreadLocal = new BasicMDCAdapter$1(this);

   public BasicMDCAdapter() {
      super();
   }

   public void put(String var1, String var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("key cannot be null");
      } else {
         Object var3 = (Map)this.inheritableThreadLocal.get();
         if (var3 == null) {
            var3 = new HashMap();
            this.inheritableThreadLocal.set(var3);
         }

         ((Map)var3).put(var1, var2);
      }
   }

   public String get(String var1) {
      Map var2 = (Map)this.inheritableThreadLocal.get();
      return var2 != null && var1 != null ? (String)var2.get(var1) : null;
   }

   public void remove(String var1) {
      Map var2 = (Map)this.inheritableThreadLocal.get();
      if (var2 != null) {
         var2.remove(var1);
      }

   }

   public void clear() {
      Map var1 = (Map)this.inheritableThreadLocal.get();
      if (var1 != null) {
         var1.clear();
         this.inheritableThreadLocal.remove();
      }

   }

   public Set getKeys() {
      Map var1 = (Map)this.inheritableThreadLocal.get();
      return var1 != null ? var1.keySet() : null;
   }

   public Map getCopyOfContextMap() {
      Map var1 = (Map)this.inheritableThreadLocal.get();
      return var1 != null ? new HashMap(var1) : null;
   }

   public void setContextMap(Map var1) {
      this.inheritableThreadLocal.set(new HashMap(var1));
   }
}
