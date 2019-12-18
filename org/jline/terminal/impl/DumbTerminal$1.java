package org.jline.terminal.impl;

import java.io.IOException;
import org.jline.terminal.Attributes$ControlChar;
import org.jline.terminal.Attributes$InputFlag;
import org.jline.terminal.Attributes$LocalFlag;
import org.jline.terminal.Terminal$Signal;
import org.jline.utils.NonBlockingInputStream;

class DumbTerminal$1 extends NonBlockingInputStream {
   final NonBlockingInputStream val$nbis;
   final DumbTerminal this$0;

   DumbTerminal$1(DumbTerminal var1, NonBlockingInputStream var2) {
      super();
      this.this$0 = var1;
      this.val$nbis = var2;
   }

   public int read(long var1, boolean var3) throws IOException {
      while(true) {
         int var4 = this.val$nbis.read(var1, var3);
         if (DumbTerminal.access$000(this.this$0).getLocalFlag(Attributes$LocalFlag.ISIG)) {
            if (var4 == DumbTerminal.access$000(this.this$0).getControlChar(Attributes$ControlChar.VINTR)) {
               this.this$0.raise(Terminal$Signal.INT);
               continue;
            }

            if (var4 == DumbTerminal.access$000(this.this$0).getControlChar(Attributes$ControlChar.VQUIT)) {
               this.this$0.raise(Terminal$Signal.QUIT);
               continue;
            }

            if (var4 == DumbTerminal.access$000(this.this$0).getControlChar(Attributes$ControlChar.VSUSP)) {
               this.this$0.raise(Terminal$Signal.TSTP);
               continue;
            }

            if (var4 == DumbTerminal.access$000(this.this$0).getControlChar(Attributes$ControlChar.VSTATUS)) {
               this.this$0.raise(Terminal$Signal.INFO);
               continue;
            }
         }

         if (var4 == 13) {
            if (DumbTerminal.access$000(this.this$0).getInputFlag(Attributes$InputFlag.IGNCR)) {
               continue;
            }

            if (DumbTerminal.access$000(this.this$0).getInputFlag(Attributes$InputFlag.ICRNL)) {
               var4 = 10;
            }
         } else if (var4 == 10 && DumbTerminal.access$000(this.this$0).getInputFlag(Attributes$InputFlag.INLCR)) {
            var4 = 13;
         }

         return var4;
      }
   }
}
