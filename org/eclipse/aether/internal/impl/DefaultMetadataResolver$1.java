package org.eclipse.aether.internal.impl;

import java.util.concurrent.Executor;

class DefaultMetadataResolver$1 implements Executor {
   final DefaultMetadataResolver this$0;

   DefaultMetadataResolver$1(DefaultMetadataResolver var1) {
      super();
      this.this$0 = var1;
   }

   public void execute(Runnable var1) {
      var1.run();
   }
}
