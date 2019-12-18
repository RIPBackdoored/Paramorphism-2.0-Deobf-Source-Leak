package com.fasterxml.jackson.core.io;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

public class DataOutputAsStream extends OutputStream {
   protected final DataOutput _output;

   public DataOutputAsStream(DataOutput var1) {
      super();
      this._output = var1;
   }

   public void write(int var1) throws IOException {
      this._output.write(var1);
   }

   public void write(byte[] var1) throws IOException {
      this._output.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this._output.write(var1, var2, var3);
   }
}
