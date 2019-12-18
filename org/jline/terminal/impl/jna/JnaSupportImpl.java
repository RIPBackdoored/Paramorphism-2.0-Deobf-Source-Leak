package org.jline.terminal.impl.jna;

import java.io.IOException;
import java.nio.charset.Charset;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$SignalHandler;
import org.jline.terminal.impl.jna.win.JnaWinSysTerminal;
import org.jline.terminal.spi.JnaSupport;
import org.jline.terminal.spi.Pty;

public class JnaSupportImpl implements JnaSupport {
   public JnaSupportImpl() {
      super();
   }

   public Pty current() throws IOException {
      return JnaNativePty.current();
   }

   public Pty open(Attributes var1, Size var2) throws IOException {
      return JnaNativePty.open(var1, var2);
   }

   public Terminal winSysTerminal(String var1, String var2, boolean var3, Charset var4, int var5, boolean var6, Terminal$SignalHandler var7) throws IOException {
      return this.winSysTerminal(var1, var2, var3, var4, var5, var6, var7, false);
   }

   public Terminal winSysTerminal(String var1, String var2, boolean var3, Charset var4, int var5, boolean var6, Terminal$SignalHandler var7, boolean var8) throws IOException {
      return JnaWinSysTerminal.createTerminal(var1, var2, var3, var4, var5, var6, var7, var8);
   }
}
