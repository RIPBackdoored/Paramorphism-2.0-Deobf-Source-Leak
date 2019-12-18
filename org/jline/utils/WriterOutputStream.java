package org.jline.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class WriterOutputStream extends OutputStream {
   private final Writer out;
   private final CharsetDecoder decoder;
   private final ByteBuffer decoderIn;
   private final CharBuffer decoderOut;

   public WriterOutputStream(Writer var1, Charset var2) {
      this(var1, var2.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
   }

   public WriterOutputStream(Writer var1, CharsetDecoder var2) {
      super();
      this.decoderIn = ByteBuffer.allocate(256);
      this.decoderOut = CharBuffer.allocate(128);
      this.out = var1;
      this.decoder = var2;
   }

   public void write(int var1) throws IOException {
      this.write(new byte[]{(byte)var1}, 0, 1);
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      while(var3 > 0) {
         int var4 = Math.min(var3, this.decoderIn.remaining());
         this.decoderIn.put(var1, var2, var4);
         this.processInput(false);
         var3 -= var4;
         var2 += var4;
      }

      this.flush();
   }

   public void flush() throws IOException {
      this.flushOutput();
      this.out.flush();
   }

   public void close() throws IOException {
      this.processInput(true);
      this.flush();
      this.out.close();
   }

   private void processInput(boolean var1) throws IOException {
      this.decoderIn.flip();

      while(true) {
         CoderResult var2 = this.decoder.decode(this.decoderIn, this.decoderOut, var1);
         if (!var2.isOverflow()) {
            if (var2.isUnderflow()) {
               this.decoderIn.compact();
               return;
            } else {
               throw new IOException("Unexpected coder result");
            }
         }

         this.flushOutput();
      }
   }

   private void flushOutput() throws IOException {
      if (this.decoderOut.position() > 0) {
         this.out.write(this.decoderOut.array(), 0, this.decoderOut.position());
         this.decoderOut.rewind();
      }

   }
}
