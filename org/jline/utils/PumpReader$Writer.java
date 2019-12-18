package org.jline.utils;

import java.io.IOException;
import java.io.Writer;

class PumpReader$Writer extends Writer {
   private final PumpReader reader;

   private PumpReader$Writer(PumpReader var1) {
      super();
      this.reader = var1;
   }

   public void write(int var1) throws IOException {
      this.reader.write((char)var1);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      this.reader.write(var1, var2, var3);
   }

   public void write(String var1, int var2, int var3) throws IOException {
      this.reader.write(var1, var2, var3);
   }

   public void flush() throws IOException {
      this.reader.flush();
   }

   public void close() throws IOException {
      this.reader.close();
   }

   PumpReader$Writer(PumpReader var1, PumpReader$1 var2) {
      this(var1);
   }
}
