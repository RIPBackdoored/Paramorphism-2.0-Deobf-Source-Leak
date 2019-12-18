package org.jline.terminal.impl.jansi.win;

import java.io.IOException;
import org.fusesource.jansi.internal.Kernel32;
import org.fusesource.jansi.internal.WindowsSupport;
import org.jline.terminal.impl.AbstractWindowsConsoleWriter;

class JansiWinConsoleWriter extends AbstractWindowsConsoleWriter {
   private static final long console;
   private final int[] writtenChars = new int[1];

   JansiWinConsoleWriter() {
      super();
   }

   protected void writeConsole(char[] var1, int var2) throws IOException {
      if (Kernel32.WriteConsoleW(console, var1, var2, this.writtenChars, 0L) == 0) {
         throw new IOException("Failed to write to console: " + WindowsSupport.getLastErrorMessage());
      }
   }

   static {
      console = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
   }
}
