package org.eclipse.aether.internal.impl.collect;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

class ObjectPool {
   private final Map objects = new WeakHashMap(256);

   ObjectPool() {
      super();
   }

   public synchronized Object intern(Object var1) {
      Reference var2 = (Reference)this.objects.get(var1);
      if (var2 != null) {
         Object var3 = var2.get();
         if (var3 != null) {
            return var3;
         }
      }

      this.objects.put(var1, new WeakReference(var1));
      return var1;
   }
}
