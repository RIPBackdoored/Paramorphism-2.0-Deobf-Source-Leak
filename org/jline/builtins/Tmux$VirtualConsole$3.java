package org.jline.builtins;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;
import org.jline.terminal.impl.LineDisciplineTerminal;

class Tmux$VirtualConsole$3 extends LineDisciplineTerminal {
   final Consumer val$closer;
   final Tmux$VirtualConsole this$0;

   Tmux$VirtualConsole$3(Tmux$VirtualConsole var1, String var2, String var3, OutputStream var4, Charset var5, Consumer var6) {
      super(var2, var3, var4, var5);
      this.this$0 = var1;
      this.val$closer = var6;
   }

   public void close() throws IOException {
      super.close();
      this.val$closer.accept(this.this$0);
   }
}
