package org.jline.utils;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttributedString extends AttributedCharSequence {
   final char[] buffer;
   final int[] style;
   final int start;
   final int end;
   public static final AttributedString EMPTY = new AttributedString("");
   public static final AttributedString NEWLINE = new AttributedString("\n");

   public AttributedString(CharSequence var1) {
      this(var1, 0, var1.length(), (AttributedStyle)null);
   }

   public AttributedString(CharSequence var1, int var2, int var3) {
      this(var1, var2, var3, (AttributedStyle)null);
   }

   public AttributedString(CharSequence var1, AttributedStyle var2) {
      this(var1, 0, var1.length(), var2);
   }

   public AttributedString(CharSequence var1, int var2, int var3, AttributedStyle var4) {
      super();
      if (var3 < var2) {
         throw new InvalidParameterException();
      } else {
         int var6;
         if (var1 instanceof AttributedString) {
            AttributedString var5 = (AttributedString)var1;
            this.buffer = var5.buffer;
            if (var4 != null) {
               this.style = (int[])var5.style.clone();

               for(var6 = 0; var6 < this.style.length; ++var6) {
                  this.style[var6] = this.style[var6] & ~var4.getMask() | var4.getStyle();
               }
            } else {
               this.style = var5.style;
            }

            this.start = var5.start + var2;
            this.end = var5.start + var3;
         } else if (var1 instanceof AttributedStringBuilder) {
            AttributedStringBuilder var8 = (AttributedStringBuilder)var1;
            AttributedString var10 = var8.subSequence(var2, var3);
            this.buffer = var10.buffer;
            this.style = var10.style;
            if (var4 != null) {
               for(int var7 = 0; var7 < this.style.length; ++var7) {
                  this.style[var7] = this.style[var7] & ~var4.getMask() | var4.getStyle();
               }
            }

            this.start = var10.start;
            this.end = var10.end;
         } else {
            int var9 = var3 - var2;
            this.buffer = new char[var9];

            for(var6 = 0; var6 < var9; ++var6) {
               this.buffer[var6] = var1.charAt(var2 + var6);
            }

            this.style = new int[var9];
            if (var4 != null) {
               Arrays.fill(this.style, var4.getStyle());
            }

            this.start = 0;
            this.end = var9;
         }

      }
   }

   AttributedString(char[] var1, int[] var2, int var3, int var4) {
      super();
      this.buffer = var1;
      this.style = var2;
      this.start = var3;
      this.end = var4;
   }

   public static AttributedString fromAnsi(String var0) {
      return fromAnsi(var0, 0);
   }

   public static AttributedString fromAnsi(String var0, int var1) {
      return var0 == null ? null : (new AttributedStringBuilder(var0.length())).tabs(var1).ansiAppend(var0).toAttributedString();
   }

   public static String stripAnsi(String var0) {
      return var0 == null ? null : (new AttributedStringBuilder(var0.length())).ansiAppend(var0).toString();
   }

   protected char[] buffer() {
      return this.buffer;
   }

   protected int offset() {
      return this.start;
   }

   public int length() {
      return this.end - this.start;
   }

   public AttributedStyle styleAt(int var1) {
      return new AttributedStyle(this.style[this.start + var1], this.style[this.start + var1]);
   }

   int styleCodeAt(int var1) {
      return this.style[this.start + var1];
   }

   public AttributedString subSequence(int var1, int var2) {
      return new AttributedString(this, var1, var2);
   }

   public AttributedString styleMatches(Pattern var1, AttributedStyle var2) {
      Matcher var3 = var1.matcher(this);
      boolean var4 = var3.find();
      if (!var4) {
         return this;
      } else {
         int[] var5 = (int[])this.style.clone();

         do {
            for(int var6 = var3.start(); var6 < var3.end(); ++var6) {
               var5[this.start + var6] = var5[this.start + var6] & ~var2.getMask() | var2.getStyle();
            }

            var4 = var3.find();
         } while(var4);

         return new AttributedString(this.buffer, var5, this.start, this.end);
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         AttributedString var2 = (AttributedString)var1;
         return this.end - this.start == var2.end - var2.start && this.arrEq(this.buffer, var2.buffer, this.start, var2.start, this.end - this.start) && this.arrEq(this.style, var2.style, this.start, var2.start, this.end - this.start);
      } else {
         return false;
      }
   }

   private boolean arrEq(char[] var1, char[] var2, int var3, int var4, int var5) {
      for(int var6 = 0; var6 < var5; ++var6) {
         if (var1[var3 + var6] != var2[var4 + var6]) {
            return false;
         }
      }

      return true;
   }

   private boolean arrEq(int[] var1, int[] var2, int var3, int var4, int var5) {
      for(int var6 = 0; var6 < var5; ++var6) {
         if (var1[var3 + var6] != var2[var4 + var6]) {
            return false;
         }
      }

      return true;
   }

   public int hashCode() {
      int var1 = Arrays.hashCode(this.buffer);
      var1 = 31 * var1 + Arrays.hashCode(this.style);
      var1 = 31 * var1 + this.start;
      var1 = 31 * var1 + this.end;
      return var1;
   }

   public static AttributedString join(AttributedString var0, AttributedString... var1) {
      Objects.requireNonNull(var0);
      Objects.requireNonNull(var1);
      return join(var0, (Iterable)Arrays.asList(var1));
   }

   public static AttributedString join(AttributedString var0, Iterable var1) {
      Objects.requireNonNull(var1);
      AttributedStringBuilder var2 = new AttributedStringBuilder();
      int var3 = 0;

      AttributedString var5;
      for(Iterator var4 = var1.iterator(); var4.hasNext(); var2.append(var5)) {
         var5 = (AttributedString)var4.next();
         if (var3++ > 0 && var0 != null) {
            var2.append(var0);
         }
      }

      return var2.toAttributedString();
   }

   public CharSequence subSequence(int var1, int var2) {
      return this.subSequence(var1, var2);
   }
}
