package org.jline.builtins.telnet;

import java.io.PrintStream;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

class Telnet$1$1 extends Connection {
   TelnetIO telnetIO;
   final Telnet$1 this$1;

   Telnet$1$1(Telnet$1 var1, ThreadGroup var2, ConnectionData var3) {
      super(var2, var3);
      this.this$1 = var1;
   }

   protected void doRun() throws Exception {
      this.telnetIO = new TelnetIO();
      this.telnetIO.setConnection(this);
      this.telnetIO.initIO();
      Telnet$1$1$1 var1 = new Telnet$1$1$1(this);
      PrintStream var2 = new PrintStream(new Telnet$1$1$2(this));
      Terminal var3 = TerminalBuilder.builder().type(this.getConnectionData().getNegotiatedTerminalType().toLowerCase()).streams(var1, var2).system(false).name("telnet").build();
      var3.setSize(new Size(this.getConnectionData().getTerminalColumns(), this.getConnectionData().getTerminalRows()));
      var3.setAttributes(Telnet.access$000(this.this$1.this$0).getAttributes());
      this.addConnectionListener(new Telnet$1$1$3(this, var3));
      boolean var6 = false;

      try {
         var6 = true;
         Telnet.access$100(this.this$1.this$0).shell(var3, this.getConnectionData().getEnvironment());
         var6 = false;
      } finally {
         if (var6) {
            this.close();
         }
      }

      this.close();
   }

   protected void doClose() throws Exception {
      this.telnetIO.closeOutput();
      this.telnetIO.closeInput();
   }
}
