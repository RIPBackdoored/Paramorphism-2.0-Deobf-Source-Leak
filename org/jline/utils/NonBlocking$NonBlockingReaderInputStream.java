package org.jline.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

class NonBlocking$NonBlockingReaderInputStream extends NonBlockingInputStream {
   private final NonBlockingReader reader;
   private final CharsetEncoder encoder;
   private final ByteBuffer bytes;
   private final CharBuffer chars;

   private NonBlocking$NonBlockingReaderInputStream(NonBlockingReader var1, Charset var2) {
      super();
      this.reader = var1;
      this.encoder = var2.newEncoder().onUnmappableCharacter(CodingErrorAction.REPLACE).onMalformedInput(CodingErrorAction.REPLACE);
      this.bytes = ByteBuffer.allocate(4);
      this.chars = CharBuffer.allocate(2);
      this.bytes.limit(0);
      this.chars.limit(0);
   }

   public int available() {
      return (int)((float)this.reader.available() * this.encoder.averageBytesPerChar()) + this.bytes.remaining();
   }

   public void close() throws IOException {
      this.reader.close();
   }

   public int read(long var1, boolean var3) throws IOException {
      boolean var4 = var1 <= 0L;

      while(!this.bytes.hasRemaining() && (var4 || var1 > 0L)) {
         long var5 = 0L;
         if (!var4) {
            var5 = System.currentTimeMillis();
         }

         int var7 = this.reader.read(var1);
         if (var7 == -1) {
            return -1;
         }

         if (var7 >= 0) {
            if (!this.chars.hasRemaining()) {
               this.chars.position(0);
               this.chars.limit(0);
            }

            int var8 = this.chars.limit();
            this.chars.array()[this.chars.arrayOffset() + var8] = (char)var7;
            this.chars.limit(var8 + 1);
            this.bytes.clear();
            this.encoder.encode(this.chars, this.bytes, false);
            this.bytes.flip();
         }

         if (!var4) {
            var1 -= System.currentTimeMillis() - var5;
         }
      }

      if (this.bytes.hasRemaining()) {
         return var3 ? this.bytes.get(this.bytes.position()) : this.bytes.get();
      } else {
         return -2;
      }
   }

   NonBlocking$NonBlockingReaderInputStream(NonBlockingReader var1, Charset var2, NonBlocking$1 var3) {
      this(var1, var2);
   }
}
