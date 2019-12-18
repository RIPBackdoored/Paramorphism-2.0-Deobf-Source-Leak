package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import java.io.IOException;

class UnicodeEscaper$1 implements Appendable {
   int pendingHighSurrogate;
   char[] decodedChars;
   final Appendable val$out;
   final UnicodeEscaper this$0;

   UnicodeEscaper$1(UnicodeEscaper var1, Appendable var2) {
      super();
      this.this$0 = var1;
      this.val$out = var2;
      this.pendingHighSurrogate = -1;
      this.decodedChars = new char[2];
   }

   public Appendable append(CharSequence var1) throws IOException {
      return this.append(var1, 0, var1.length());
   }

   public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
      int var4 = var2;
      if (var2 < var3) {
         int var5 = var2;
         char[] var7;
         if (this.pendingHighSurrogate != -1) {
            var4 = var2 + 1;
            char var6 = var1.charAt(var2);
            if (!Character.isLowSurrogate(var6)) {
               throw new IllegalArgumentException("Expected low surrogate character but got " + var6);
            }

            var7 = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, var6));
            if (var7 != null) {
               this.outputChars(var7, var7.length);
               var5 = var2 + 1;
            } else {
               this.val$out.append((char)this.pendingHighSurrogate);
            }

            this.pendingHighSurrogate = -1;
         }

         while(true) {
            var4 = this.this$0.nextEscapeIndex(var1, var4, var3);
            if (var4 > var5) {
               this.val$out.append(var1, var5, var4);
            }

            if (var4 == var3) {
               break;
            }

            int var9 = UnicodeEscaper.codePointAt(var1, var4, var3);
            if (var9 < 0) {
               this.pendingHighSurrogate = -var9;
               break;
            }

            var7 = this.this$0.escape(var9);
            if (var7 != null) {
               this.outputChars(var7, var7.length);
            } else {
               int var8 = Character.toChars(var9, this.decodedChars, 0);
               this.outputChars(this.decodedChars, var8);
            }

            var4 += Character.isSupplementaryCodePoint(var9) ? 2 : 1;
            var5 = var4;
         }
      }

      return this;
   }

   public Appendable append(char var1) throws IOException {
      char[] var2;
      if (this.pendingHighSurrogate != -1) {
         if (!Character.isLowSurrogate(var1)) {
            throw new IllegalArgumentException("Expected low surrogate character but got '" + var1 + "' with value " + var1);
         }

         var2 = this.this$0.escape(Character.toCodePoint((char)this.pendingHighSurrogate, var1));
         if (var2 != null) {
            this.outputChars(var2, var2.length);
         } else {
            this.val$out.append((char)this.pendingHighSurrogate);
            this.val$out.append(var1);
         }

         this.pendingHighSurrogate = -1;
      } else if (Character.isHighSurrogate(var1)) {
         this.pendingHighSurrogate = var1;
      } else {
         if (Character.isLowSurrogate(var1)) {
            throw new IllegalArgumentException("Unexpected low surrogate character '" + var1 + "' with value " + var1);
         }

         var2 = this.this$0.escape(var1);
         if (var2 != null) {
            this.outputChars(var2, var2.length);
         } else {
            this.val$out.append(var1);
         }
      }

      return this;
   }

   private void outputChars(char[] var1, int var2) throws IOException {
      for(int var3 = 0; var3 < var2; ++var3) {
         this.val$out.append(var1[var3]);
      }

   }
}
