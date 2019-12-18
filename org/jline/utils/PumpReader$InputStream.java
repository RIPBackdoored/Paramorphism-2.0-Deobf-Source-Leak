package org.jline.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

class PumpReader$InputStream extends InputStream {
   private final PumpReader reader;
   private final CharsetEncoder encoder;
   private final ByteBuffer buffer;

   private PumpReader$InputStream(PumpReader var1, Charset var2) {
      super();
      this.reader = var1;
      this.encoder = var2.newEncoder().onUnmappableCharacter(CodingErrorAction.REPLACE).onMalformedInput(CodingErrorAction.REPLACE);
      this.buffer = ByteBuffer.allocate((int)Math.ceil((double)this.encoder.maxBytesPerChar()));
      this.buffer.limit(0);
   }

   public int available() throws IOException {
      return (int)((double)this.reader.available() * (double)this.encoder.averageBytesPerChar()) + this.buffer.remaining();
   }

   public int read() throws IOException {
      return !this.buffer.hasRemaining() && !this.readUsingBuffer() ? -1 : this.buffer.get();
   }

   private boolean readUsingBuffer() throws IOException {
      this.buffer.clear();
      this.reader.readBytes(this.encoder, this.buffer);
      this.buffer.flip();
      return this.buffer.hasRemaining();
   }

   private int copyFromBuffer(byte[] var1, int var2, int var3) {
      var3 = Math.min(var3, this.buffer.remaining());
      this.buffer.get(var1, var2, var3);
      return var3;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         return 0;
      } else {
         int var4;
         if (this.buffer.hasRemaining()) {
            var4 = this.copyFromBuffer(var1, var2, var3);
            if (var4 == var3) {
               return var3;
            }

            var2 += var4;
            var3 -= var4;
         } else {
            var4 = 0;
         }

         if (var3 >= this.buffer.capacity()) {
            var4 += this.reader.readBytes(this.encoder, var1, var2, var3);
         } else if (this.readUsingBuffer()) {
            var4 += this.copyFromBuffer(var1, var2, var3);
         }

         return var4 == 0 ? -1 : var4;
      }
   }

   public void close() throws IOException {
      this.reader.close();
   }

   PumpReader$InputStream(PumpReader var1, Charset var2, PumpReader$1 var3) {
      this(var1, var2);
   }
}
