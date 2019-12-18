package org.jline.terminal.impl;

import java.io.IOException;
import java.io.Writer;

public abstract class AbstractWindowsConsoleWriter extends Writer {
   public AbstractWindowsConsoleWriter() {
      super();
   }

   protected abstract void writeConsole(char[] var1, int var2) throws IOException;

   public void write(char[] var1, int var2, int var3) throws IOException {
      char[] var4 = var1;
      if (var2 != 0) {
         var4 = new char[var3];
         System.arraycopy(var1, var2, var4, 0, var3);
      }

      synchronized(this.lock) {
         this.writeConsole(var4, var3);
      }

   }

   public void flush() {
   }

   public void close() {
   }
}
