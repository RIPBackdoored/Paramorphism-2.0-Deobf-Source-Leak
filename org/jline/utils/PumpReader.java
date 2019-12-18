package org.jline.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class PumpReader extends Reader {
   private static final int EOF = -1;
   private static final int DEFAULT_BUFFER_SIZE = 4096;
   private final CharBuffer readBuffer;
   private final CharBuffer writeBuffer;
   private final PumpReader$Writer writer;
   private boolean closed;

   public PumpReader() {
      this(4096);
   }

   public PumpReader(int var1) {
      super();
      char[] var2 = new char[var1];
      this.readBuffer = CharBuffer.wrap(var2);
      this.writeBuffer = CharBuffer.wrap(var2);
      this.writer = new PumpReader$Writer(this, (PumpReader$1)null);
      this.readBuffer.limit(0);
   }

   public java.io.Writer getWriter() {
      return this.writer;
   }

   public java.io.InputStream createInputStream(Charset var1) {
      return new PumpReader$InputStream(this, var1, (PumpReader$1)null);
   }

   private boolean wait(CharBuffer var1) throws InterruptedIOException {
      if (this.closed) {
         return false;
      } else {
         while(!var1.hasRemaining()) {
            this.notifyAll();

            try {
               this.wait();
            } catch (InterruptedException var3) {
               throw new InterruptedIOException();
            }

            if (this.closed) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean waitForInput() throws InterruptedIOException {
      return this.wait(this.readBuffer);
   }

   private void waitForBufferSpace() throws InterruptedIOException, ClosedException {
      if (!this.wait(this.writeBuffer)) {
         throw new ClosedException();
      }
   }

   private static boolean rewind(CharBuffer var0, CharBuffer var1) {
      if (var0.position() > var1.position()) {
         var1.limit(var0.position());
      }

      if (var0.position() == var0.capacity()) {
         var0.rewind();
         var0.limit(var1.position());
         return true;
      } else {
         return false;
      }
   }

   private boolean rewindReadBuffer() {
      return rewind(this.readBuffer, this.writeBuffer) && this.readBuffer.hasRemaining();
   }

   private void rewindWriteBuffer() {
      rewind(this.writeBuffer, this.readBuffer);
   }

   public synchronized boolean ready() {
      return this.readBuffer.hasRemaining();
   }

   public synchronized int available() {
      int var1 = this.readBuffer.remaining();
      if (this.writeBuffer.position() < this.readBuffer.position()) {
         var1 += this.writeBuffer.position();
      }

      return var1;
   }

   public synchronized int read() throws IOException {
      if (!this.waitForInput()) {
         return -1;
      } else {
         char var1 = this.readBuffer.get();
         this.rewindReadBuffer();
         return var1;
      }
   }

   private int copyFromBuffer(char[] var1, int var2, int var3) {
      var3 = Math.min(var3, this.readBuffer.remaining());
      this.readBuffer.get(var1, var2, var3);
      return var3;
   }

   public synchronized int read(char[] var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         return 0;
      } else if (!this.waitForInput()) {
         return -1;
      } else {
         int var4 = this.copyFromBuffer(var1, var2, var3);
         if (this.rewindReadBuffer() && var4 < var3) {
            var4 += this.copyFromBuffer(var1, var2 + var4, var3 - var4);
            this.rewindReadBuffer();
         }

         return var4;
      }
   }

   public int read(CharBuffer var1) throws IOException {
      if (!var1.hasRemaining()) {
         return 0;
      } else if (!this.waitForInput()) {
         return -1;
      } else {
         int var2 = this.readBuffer.read(var1);
         if (this.rewindReadBuffer() && var1.hasRemaining()) {
            var2 += this.readBuffer.read(var1);
            this.rewindReadBuffer();
         }

         return var2;
      }
   }

   private void encodeBytes(CharsetEncoder var1, ByteBuffer var2) throws IOException {
      CoderResult var3 = var1.encode(this.readBuffer, var2, false);
      if (this.rewindReadBuffer() && var3.isUnderflow()) {
         var1.encode(this.readBuffer, var2, false);
         this.rewindReadBuffer();
      }

   }

   synchronized int readBytes(CharsetEncoder var1, byte[] var2, int var3, int var4) throws IOException {
      if (!this.waitForInput()) {
         return 0;
      } else {
         ByteBuffer var5 = ByteBuffer.wrap(var2, var3, var4);
         this.encodeBytes(var1, var5);
         return var5.position() - var3;
      }
   }

   synchronized void readBytes(CharsetEncoder var1, ByteBuffer var2) throws IOException {
      if (this.waitForInput()) {
         this.encodeBytes(var1, var2);
      }
   }

   synchronized void write(char var1) throws IOException {
      this.waitForBufferSpace();
      this.writeBuffer.put(var1);
      this.rewindWriteBuffer();
   }

   synchronized void write(char[] var1, int var2, int var3) throws IOException {
      while(var3 > 0) {
         this.waitForBufferSpace();
         int var4 = Math.min(var3, this.writeBuffer.remaining());
         this.writeBuffer.put(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         this.rewindWriteBuffer();
      }

   }

   synchronized void write(String var1, int var2, int var3) throws IOException {
      char[] var4 = this.writeBuffer.array();

      while(var3 > 0) {
         this.waitForBufferSpace();
         int var5 = Math.min(var3, this.writeBuffer.remaining());
         var1.getChars(var2, var2 + var5, var4, this.writeBuffer.position());
         this.writeBuffer.position(this.writeBuffer.position() + var5);
         var2 += var5;
         var3 -= var5;
         this.rewindWriteBuffer();
      }

   }

   synchronized void flush() {
      if (this.readBuffer.hasRemaining()) {
         this.notifyAll();
      }

   }

   public synchronized void close() throws IOException {
      this.closed = true;
      this.notifyAll();
   }
}
