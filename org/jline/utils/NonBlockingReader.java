package org.jline.utils;

import java.io.IOException;
import java.io.Reader;

public abstract class NonBlockingReader extends Reader {
   public static final int EOF = -1;
   public static final int READ_EXPIRED = -2;

   public NonBlockingReader() {
      super();
   }

   public void shutdown() {
   }

   public int read() throws IOException {
      return this.read(0L, false);
   }

   public int peek(long var1) throws IOException {
      return this.read(var1, true);
   }

   public int read(long var1) throws IOException {
      return this.read(var1, false);
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (var1 == null) {
         throw new NullPointerException();
      } else if (var2 >= 0 && var3 >= 0 && var3 <= var1.length - var2) {
         if (var3 == 0) {
            return 0;
         } else {
            int var4 = this.read(0L);
            if (var4 == -1) {
               return -1;
            } else {
               var1[var2] = (char)var4;
               return 1;
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int available() {
      return 0;
   }

   protected abstract int read(long var1, boolean var3) throws IOException;
}
