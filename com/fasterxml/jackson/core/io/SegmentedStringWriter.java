package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.Writer;

public final class SegmentedStringWriter extends Writer {
   private final TextBuffer _buffer;

   public SegmentedStringWriter(BufferRecycler var1) {
      super();
      this._buffer = new TextBuffer(var1);
   }

   public Writer append(char var1) {
      this.write(var1);
      return this;
   }

   public Writer append(CharSequence var1) {
      String var2 = var1.toString();
      this._buffer.append((String)var2, 0, var2.length());
      return this;
   }

   public Writer append(CharSequence var1, int var2, int var3) {
      String var4 = var1.subSequence(var2, var3).toString();
      this._buffer.append((String)var4, 0, var4.length());
      return this;
   }

   public void close() {
   }

   public void flush() {
   }

   public void write(char[] var1) {
      this._buffer.append((char[])var1, 0, var1.length);
   }

   public void write(char[] var1, int var2, int var3) {
      this._buffer.append(var1, var2, var3);
   }

   public void write(int var1) {
      this._buffer.append((char)var1);
   }

   public void write(String var1) {
      this._buffer.append((String)var1, 0, var1.length());
   }

   public void write(String var1, int var2, int var3) {
      this._buffer.append(var1, var2, var3);
   }

   public String getAndClear() {
      String var1 = this._buffer.contentsAsString();
      this._buffer.releaseBuffers();
      return var1;
   }

   public Appendable append(char var1) throws IOException {
      return this.append(var1);
   }

   public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
      return this.append(var1, var2, var3);
   }

   public Appendable append(CharSequence var1) throws IOException {
      return this.append(var1);
   }
}
