package org.jline.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import java.nio.CharBuffer;

public class NonBlockingPumpReader extends NonBlockingReader {
   private static final int DEFAULT_BUFFER_SIZE = 4096;
   private final CharBuffer readBuffer;
   private final CharBuffer writeBuffer;
   private final Writer writer;
   private boolean closed;

   public NonBlockingPumpReader() {
      this(4096);
   }

   public NonBlockingPumpReader(int var1) {
      super();
      char[] var2 = new char[var1];
      this.readBuffer = CharBuffer.wrap(var2);
      this.writeBuffer = CharBuffer.wrap(var2);
      this.writer = new NonBlockingPumpReader$NbpWriter(this, (NonBlockingPumpReader$1)null);
      this.readBuffer.limit(0);
   }

   public Writer getWriter() {
      return this.writer;
   }

   private int wait(CharBuffer var1, long var2) throws InterruptedIOException {
      boolean var4 = var2 <= 0L;
      long var5 = 0L;
      if (!var4) {
         var5 = System.currentTimeMillis() + var2;
      }

      while(!this.closed && !var1.hasRemaining() && (var4 || var2 > 0L)) {
         this.notifyAll();

         try {
            this.wait(var2);
         } catch (InterruptedException var8) {
            throw new InterruptedIOException();
         }

         if (!var4) {
            var2 = var5 - System.currentTimeMillis();
         }
      }

      return this.closed ? -1 : (var1.hasRemaining() ? 0 : -2);
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

   protected synchronized int read(long var1, boolean var3) throws IOException {
      int var4 = this.wait(this.readBuffer, var1);
      if (var4 >= 0) {
         var4 = var3 ? this.readBuffer.get(this.readBuffer.position()) : this.readBuffer.get();
      }

      rewind(this.readBuffer, this.writeBuffer);
      return var4;
   }

   synchronized void write(char[] var1, int var2, int var3) throws IOException {
      while(var3 > 0) {
         if (this.wait(this.writeBuffer, 0L) == -1) {
            throw new ClosedException();
         }

         int var4 = Math.min(var3, this.writeBuffer.remaining());
         this.writeBuffer.put(var1, var2, var4);
         var2 += var4;
         var3 -= var4;
         rewind(this.writeBuffer, this.readBuffer);
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
