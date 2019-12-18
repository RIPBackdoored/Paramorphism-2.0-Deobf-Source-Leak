package org.jline.builtins.telnet;

import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$Signal;

class Telnet$1$1$3 implements ConnectionListener {
   final Terminal val$terminal;
   final Telnet$1$1 this$2;

   Telnet$1$1$3(Telnet$1$1 var1, Terminal var2) {
      super();
      this.this$2 = var1;
      this.val$terminal = var2;
   }

   public void connectionTerminalGeometryChanged(ConnectionEvent var1) {
      this.val$terminal.setSize(new Size(this.this$2.getConnectionData().getTerminalColumns(), this.this$2.getConnectionData().getTerminalRows()));
      this.val$terminal.raise(Terminal$Signal.WINCH);
   }
}
