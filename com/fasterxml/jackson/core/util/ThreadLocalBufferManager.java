package com.fasterxml.jackson.core.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ThreadLocalBufferManager {
   private final Object RELEASE_LOCK = new Object();
   private final Map _trackedRecyclers = new ConcurrentHashMap();
   private final ReferenceQueue _refQueue = new ReferenceQueue();

   ThreadLocalBufferManager() {
      super();
   }

   public static ThreadLocalBufferManager instance() {
      return ThreadLocalBufferManager$ThreadLocalBufferManagerHolder.manager;
   }

   public int releaseBuffers() {
      synchronized(this.RELEASE_LOCK) {
         int var2 = 0;
         this.removeSoftRefsClearedByGc();

         for(Iterator var3 = this._trackedRecyclers.keySet().iterator(); var3.hasNext(); ++var2) {
            SoftReference var4 = (SoftReference)var3.next();
            var4.clear();
         }

         this._trackedRecyclers.clear();
         int var10000 = var2;
         return var10000;
      }
   }

   public SoftReference wrapAndTrack(BufferRecycler var1) {
      SoftReference var2 = new SoftReference(var1, this._refQueue);
      this._trackedRecyclers.put(var2, true);
      this.removeSoftRefsClearedByGc();
      return var2;
   }

   private void removeSoftRefsClearedByGc() {
      SoftReference var1;
      while((var1 = (SoftReference)this._refQueue.poll()) != null) {
         this._trackedRecyclers.remove(var1);
      }

   }
}
