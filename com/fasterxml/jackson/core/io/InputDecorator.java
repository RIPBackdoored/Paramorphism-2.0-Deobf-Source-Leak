package com.fasterxml.jackson.core.io;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

public abstract class InputDecorator implements Serializable {
   private static final long serialVersionUID = 1L;

   public InputDecorator() {
      super();
   }

   public abstract InputStream decorate(IOContext var1, InputStream var2) throws IOException;

   public abstract InputStream decorate(IOContext var1, byte[] var2, int var3, int var4) throws IOException;

   public DataInput decorate(IOContext var1, DataInput var2) throws IOException {
      throw new UnsupportedOperationException();
   }

   public abstract Reader decorate(IOContext var1, Reader var2) throws IOException;
}
