package com.fasterxml.jackson.core.util;

import java.util.concurrent.ConcurrentHashMap;

public final class InternCache extends ConcurrentHashMap {
   private static final long serialVersionUID = 1L;
   private static final int MAX_ENTRIES = 180;
   public static final InternCache instance = new InternCache();
   private final Object lock = new Object();

   private InternCache() {
      super(180, 0.8F, 4);
   }

   public String intern(String var1) {
      String var2 = (String)this.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         if (this.size() >= 180) {
            synchronized(this.lock) {
               if (this.size() >= 180) {
                  this.clear();
               }
            }
         }

         var2 = var1.intern();
         this.put(var2, var2);
         return var2;
      }
   }
}
