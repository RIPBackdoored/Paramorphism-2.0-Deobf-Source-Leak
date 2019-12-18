package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

public abstract class UnicodeEscaper implements Escaper {
   private static final int DEST_PAD = 32;
   private static final ThreadLocal DEST_TL = new UnicodeEscaper$2();
   static final boolean $assertionsDisabled = !UnicodeEscaper.class.desiredAssertionStatus();

   public UnicodeEscaper() {
      super();
   }

   protected abstract char[] escape(int var1);

   protected int nextEscapeIndex(CharSequence var1, int var2, int var3) {
      int var4;
      int var5;
      for(var4 = var2; var4 < var3; var4 += Character.isSupplementaryCodePoint(var5) ? 2 : 1) {
         var5 = codePointAt(var1, var4, var3);
         if (var5 < 0 || this.escape(var5) != null) {
            break;
         }
      }

      return var4;
   }

   public String escape(String var1) {
      int var2 = var1.length();
      int var3 = this.nextEscapeIndex(var1, 0, var2);
      return var3 == var2 ? var1 : this.escapeSlow(var1, var3);
   }

   protected final String escapeSlow(String var1, int var2) {
      int var3 = var1.length();
      char[] var4 = (char[])DEST_TL.get();
      int var5 = 0;

      int var6;
      int var7;
      for(var6 = 0; var2 < var3; var2 = this.nextEscapeIndex(var1, var6, var3)) {
         var7 = codePointAt(var1, var2, var3);
         if (var7 < 0) {
            throw new IllegalArgumentException("Trailing high surrogate at end of input");
         }

         char[] var8 = this.escape(var7);
         if (var8 != null) {
            int var9 = var2 - var6;
            int var10 = var5 + var9 + var8.length;
            if (var4.length < var10) {
               int var11 = var10 + (var3 - var2) + 32;
               var4 = growBuffer(var4, var5, var11);
            }

            if (var9 > 0) {
               var1.getChars(var6, var2, var4, var5);
               var5 += var9;
            }

            if (var8.length > 0) {
               System.arraycopy(var8, 0, var4, var5, var8.length);
               var5 += var8.length;
            }
         }

         var6 = var2 + (Character.isSupplementaryCodePoint(var7) ? 2 : 1);
      }

      var7 = var3 - var6;
      if (var7 > 0) {
         int var12 = var5 + var7;
         if (var4.length < var12) {
            var4 = growBuffer(var4, var5, var12);
         }

         var1.getChars(var6, var3, var4, var5);
         var5 = var12;
      }

      return new String(var4, 0, var5);
   }

   public Appendable escape(Appendable var1) {
      if (!$assertionsDisabled && var1 == null) {
         throw new AssertionError();
      } else {
         return new UnicodeEscaper$1(this, var1);
      }
   }

   protected static final int codePointAt(CharSequence var0, int var1, int var2) {
      if (var1 < var2) {
         char var3 = var0.charAt(var1++);
         if (var3 >= '\ud800' && var3 <= '\udfff') {
            if (var3 <= '\udbff') {
               if (var1 == var2) {
                  return -var3;
               } else {
                  char var4 = var0.charAt(var1);
                  if (Character.isLowSurrogate(var4)) {
                     return Character.toCodePoint(var3, var4);
                  } else {
                     throw new IllegalArgumentException("Expected low surrogate but got char '" + var4 + "' with value " + var4 + " at index " + var1);
                  }
               }
            } else {
               throw new IllegalArgumentException("Unexpected low surrogate character '" + var3 + "' with value " + var3 + " at index " + (var1 - 1));
            }
         } else {
            return var3;
         }
      } else {
         throw new IndexOutOfBoundsException("Index exceeds specified range");
      }
   }

   private static final char[] growBuffer(char[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      if (var1 > 0) {
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      return var3;
   }
}
