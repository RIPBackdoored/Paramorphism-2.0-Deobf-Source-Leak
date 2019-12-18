package org.jline.builtins;

import java.io.IOException;
import java.io.OutputStream;

class Tmux$VirtualConsole$2 extends OutputStream {
   final Tmux$VirtualConsole this$0;

   Tmux$VirtualConsole$2(Tmux$VirtualConsole var1) {
      super();
      this.this$0 = var1;
   }

   public void write(int var1) throws IOException {
      Tmux$VirtualConsole.access$700(this.this$0).processInputByte(var1);
   }
}
