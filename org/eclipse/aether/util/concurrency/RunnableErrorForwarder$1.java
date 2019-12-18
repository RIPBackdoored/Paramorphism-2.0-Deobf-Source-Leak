package org.eclipse.aether.util.concurrency;

import java.util.concurrent.locks.LockSupport;

class RunnableErrorForwarder$1 implements Runnable {
   final Runnable val$runnable;
   final RunnableErrorForwarder this$0;

   RunnableErrorForwarder$1(RunnableErrorForwarder var1, Runnable var2) {
      super();
      this.this$0 = var1;
      this.val$runnable = var2;
   }

   public void run() {
      boolean var5 = false;

      try {
         var5 = true;
         this.val$runnable.run();
         var5 = false;
      } catch (Error | RuntimeException var6) {
         RunnableErrorForwarder.access$000(this.this$0).compareAndSet((Object)null, var6);
         throw var6;
      } finally {
         if (var5) {
            RunnableErrorForwarder.access$100(this.this$0).decrementAndGet();
            LockSupport.unpark(RunnableErrorForwarder.access$200(this.this$0));
         }
      }

      RunnableErrorForwarder.access$100(this.this$0).decrementAndGet();
      LockSupport.unpark(RunnableErrorForwarder.access$200(this.this$0));
   }
}
