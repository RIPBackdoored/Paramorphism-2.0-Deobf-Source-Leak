package org.eclipse.aether.util.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class WorkerThreadFactory implements ThreadFactory {
   private final ThreadFactory factory = Executors.defaultThreadFactory();
   private final String namePrefix;
   private final AtomicInteger threadIndex;
   private static final AtomicInteger POOL_INDEX = new AtomicInteger();

   public WorkerThreadFactory(String var1) {
      super();
      this.namePrefix = (var1 != null && var1.length() > 0 ? var1 : getCallerSimpleClassName() + '-') + POOL_INDEX.getAndIncrement() + '-';
      this.threadIndex = new AtomicInteger();
   }

   private static String getCallerSimpleClassName() {
      StackTraceElement[] var0 = (new Exception()).getStackTrace();
      if (var0 != null && var0.length > 2) {
         String var1 = var0[2].getClassName();
         var1 = var1.substring(var1.lastIndexOf(46) + 1);
         return var1;
      } else {
         return "Worker-";
      }
   }

   public Thread newThread(Runnable var1) {
      Thread var2 = this.factory.newThread(var1);
      var2.setName(this.namePrefix + this.threadIndex.getAndIncrement());
      var2.setDaemon(true);
      return var2;
   }
}
