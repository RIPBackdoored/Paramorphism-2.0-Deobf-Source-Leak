package org.jline.terminal.spi;

import java.io.IOException;
import java.nio.charset.Charset;
import org.jline.terminal.Attributes;
import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal$SignalHandler;

public interface JansiSupport {
   Pty current() throws IOException;

   Pty open(Attributes var1, Size var2) throws IOException;

   Terminal winSysTerminal(String var1, String var2, boolean var3, Charset var4, int var5, boolean var6, Terminal$SignalHandler var7) throws IOException;

   Terminal winSysTerminal(String var1, String var2, boolean var3, Charset var4, int var5, boolean var6, Terminal$SignalHandler var7, boolean var8) throws IOException;
}
