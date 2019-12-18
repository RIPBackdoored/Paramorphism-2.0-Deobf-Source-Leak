package org.jline.terminal.impl.jna.win;

import com.sun.jna.LastErrorException;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import java.io.IOException;
import org.jline.terminal.impl.AbstractWindowsConsoleWriter;

class JnaWinConsoleWriter extends AbstractWindowsConsoleWriter {
   private final Pointer consoleHandle;
   private final IntByReference writtenChars = new IntByReference();

   JnaWinConsoleWriter(Pointer var1) {
      super();
      this.consoleHandle = var1;
   }

   protected void writeConsole(char[] var1, int var2) throws IOException {
      try {
         Kernel32.INSTANCE.WriteConsoleW(this.consoleHandle, var1, var2, this.writtenChars, (Pointer)null);
      } catch (LastErrorException var4) {
         throw new IOException("Failed to write to console", var4);
      }

   }
}
