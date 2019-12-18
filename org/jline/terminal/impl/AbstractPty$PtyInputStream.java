package org.jline.terminal.impl;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import org.jline.terminal.Attributes;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.utils.NonBlockingInputStream;

class AbstractPty$PtyInputStream extends NonBlockingInputStream {
   final InputStream in;
   int c;
   final AbstractPty this$0;

   AbstractPty$PtyInputStream(AbstractPty var1, InputStream var2) {
      super();
      this.this$0 = var1;
      this.c = 0;
      this.in = var2;
   }

   public int read(long var1, boolean var3) throws IOException {
      this.this$0.checkInterrupted();
      if (this.c != 0) {
         int var9 = this.c;
         if (!var3) {
            this.c = 0;
         }

         return var9;
      } else {
         this.setNonBlocking();
         long var4 = System.currentTimeMillis();

         long var7;
         do {
            int var6 = this.in.read();
            if (var6 >= 0) {
               if (var3) {
                  this.c = var6;
               }

               return var6;
            }

            this.this$0.checkInterrupted();
            var7 = System.currentTimeMillis();
         } while(var1 <= 0L || var7 - var4 <= var1);

         return -2;
      }
   }

   private void setNonBlocking() {
      if (AbstractPty.access$000(this.this$0) == null || AbstractPty.access$000(this.this$0).getControlChar(Attributes$ControlChar.VMIN) != 0 || AbstractPty.access$000(this.this$0).getControlChar(Attributes$ControlChar.VTIME) != 1) {
         try {
            Attributes var1 = this.this$0.getAttr();
            var1.setControlChar(Attributes$ControlChar.VMIN, 0);
            var1.setControlChar(Attributes$ControlChar.VTIME, 1);
            this.this$0.setAttr(var1);
         } catch (IOException var2) {
            throw new IOError(var2);
         }
      }

   }
}
