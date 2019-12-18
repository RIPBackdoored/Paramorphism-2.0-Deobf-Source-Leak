package org.jline.terminal.impl;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jline.utils.ClosedException;
import org.jline.utils.NonBlockingInputStream;

class PosixPtyTerminal$InputStreamWrapper extends NonBlockingInputStream {
   private final NonBlockingInputStream in;
   private final AtomicBoolean closed;
   final PosixPtyTerminal this$0;

   protected PosixPtyTerminal$InputStreamWrapper(PosixPtyTerminal var1, NonBlockingInputStream var2) {
      super();
      this.this$0 = var1;
      this.closed = new AtomicBoolean();
      this.in = var2;
   }

   public int read(long var1, boolean var3) throws IOException {
      if (this.closed.get()) {
         throw new ClosedException();
      } else {
         return this.in.read(var1, var3);
      }
   }

   public void close() throws IOException {
      this.closed.set(true);
   }
}
