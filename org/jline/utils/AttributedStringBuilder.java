package org.jline.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttributedStringBuilder extends AttributedCharSequence implements Appendable {
   private char[] buffer;
   private int[] style;
   private int length;
   private int tabs;
   private int lastLineLength;
   private AttributedStyle current;

   public static AttributedString append(CharSequence... var0) {
      AttributedStringBuilder var1 = new AttributedStringBuilder();
      CharSequence[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         CharSequence var5 = var2[var4];
         var1.append(var5);
      }

      return var1.toAttributedString();
   }

   public AttributedStringBuilder() {
      this(64);
   }

   public AttributedStringBuilder(int var1) {
      super();
      this.tabs = 0;
      this.lastLineLength = 0;
      this.current = AttributedStyle.DEFAULT;
      this.buffer = new char[var1];
      this.style = new int[var1];
      this.length = 0;
   }

   public int length() {
      return this.length;
   }

   public char charAt(int var1) {
      return this.buffer[var1];
   }

   public AttributedStyle styleAt(int var1) {
      return new AttributedStyle(this.style[var1], this.style[var1]);
   }

   int styleCodeAt(int var1) {
      return this.style[var1];
   }

   protected char[] buffer() {
      return this.buffer;
   }

   protected int offset() {
      return 0;
   }

   public AttributedString subSequence(int var1, int var2) {
      return new AttributedString(Arrays.copyOfRange(this.buffer, var1, var2), Arrays.copyOfRange(this.style, var1, var2), 0, var2 - var1);
   }

   public AttributedStringBuilder append(CharSequence var1) {
      return this.append(new AttributedString(var1, this.current));
   }

   public AttributedStringBuilder append(CharSequence var1, int var2, int var3) {
      return this.append(var1.subSequence(var2, var3));
   }

   public AttributedStringBuilder append(char var1) {
      return this.append((CharSequence)Character.toString(var1));
   }

   public AttributedStringBuilder append(CharSequence var1, AttributedStyle var2) {
      return this.append(new AttributedString(var1, var2));
   }

   public AttributedStringBuilder style(AttributedStyle var1) {
      this.current = var1;
      return this;
   }

   public AttributedStringBuilder style(Function var1) {
      this.current = (AttributedStyle)var1.apply(this.current);
      return this;
   }

   public AttributedStringBuilder styled(Function var1, CharSequence var2) {
      return this.styled(var1, AttributedStringBuilder::lambda$styled$0);
   }

   public AttributedStringBuilder styled(AttributedStyle var1, CharSequence var2) {
      return this.styled(AttributedStringBuilder::lambda$styled$1, AttributedStringBuilder::lambda$styled$2);
   }

   public AttributedStringBuilder styled(Function var1, Consumer var2) {
      AttributedStyle var3 = this.current;
      this.current = (AttributedStyle)var1.apply(var3);
      var2.accept(this);
      this.current = var3;
      return this;
   }

   public AttributedStyle style() {
      return this.current;
   }

   public AttributedStringBuilder append(AttributedString var1) {
      return this.append((AttributedCharSequence)var1, 0, var1.length());
   }

   public AttributedStringBuilder append(AttributedString var1, int var2, int var3) {
      return this.append((AttributedCharSequence)var1, var2, var3);
   }

   public AttributedStringBuilder append(AttributedCharSequence var1) {
      return this.append((AttributedCharSequence)var1, 0, var1.length());
   }

   public AttributedStringBuilder append(AttributedCharSequence var1, int var2, int var3) {
      this.ensureCapacity(this.length + var3 - var2);

      for(int var4 = var2; var4 < var3; ++var4) {
         char var5 = var1.charAt(var4);
         int var6 = var1.styleCodeAt(var4) & ~this.current.getMask() | this.current.getStyle();
         if (this.tabs > 0 && var5 == '\t') {
            this.insertTab(new AttributedStyle(var6, 0));
         } else {
            this.ensureCapacity(this.length + 1);
            this.buffer[this.length] = var5;
            this.style[this.length] = var6;
            if (var5 == '\n') {
               this.lastLineLength = 0;
            } else {
               ++this.lastLineLength;
            }

            ++this.length;
         }
      }

      return this;
   }

   protected void ensureCapacity(int var1) {
      if (var1 > this.buffer.length) {
         int var2;
         for(var2 = Math.max(this.buffer.length, 1); var2 <= var1; var2 *= 2) {
         }

         this.buffer = Arrays.copyOf(this.buffer, var2);
         this.style = Arrays.copyOf(this.style, var2);
      }

   }

   public void appendAnsi(String var1) {
      this.ansiAppend(var1);
   }

   public AttributedStringBuilder ansiAppend(String var1) {
      int var2 = 0;
      int var3 = 0;
      this.ensureCapacity(this.length + var1.length());

      for(int var4 = 0; var4 < var1.length(); ++var4) {
         char var5 = var1.charAt(var4);
         if (var3 == 0 && var5 == 27) {
            ++var3;
         } else if (var3 == 1 && var5 == '[') {
            ++var3;
            var2 = var4 + 1;
         } else if (var3 != 2) {
            if (var5 == '\t' && this.tabs > 0) {
               this.insertTab(this.current);
            } else {
               this.ensureCapacity(this.length + 1);
               this.buffer[this.length] = var5;
               this.style[this.length] = this.current.getStyle();
               if (var5 == '\n') {
                  this.lastLineLength = 0;
               } else {
                  ++this.lastLineLength;
               }

               ++this.length;
            }
         } else if (var5 != 'm') {
            if ((var5 < '0' || var5 > '9') && var5 != ';') {
               var3 = 0;
            }
         } else {
            String[] var6 = var1.substring(var2, var4).split(";");

            for(int var7 = 0; var7 < var6.length; ++var7) {
               int var8 = var6[var7].isEmpty() ? 0 : Integer.parseInt(var6[var7]);
               switch(var8) {
               case 0:
                  this.current = AttributedStyle.DEFAULT;
                  break;
               case 1:
                  this.current = this.current.bold();
                  break;
               case 2:
                  this.current = this.current.faint();
                  break;
               case 3:
                  this.current = this.current.italic();
                  break;
               case 4:
                  this.current = this.current.underline();
                  break;
               case 5:
                  this.current = this.current.blink();
               case 6:
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 26:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
               case 58:
               case 59:
               case 60:
               case 61:
               case 62:
               case 63:
               case 64:
               case 65:
               case 66:
               case 67:
               case 68:
               case 69:
               case 70:
               case 71:
               case 72:
               case 73:
               case 74:
               case 75:
               case 76:
               case 77:
               case 78:
               case 79:
               case 80:
               case 81:
               case 82:
               case 83:
               case 84:
               case 85:
               case 86:
               case 87:
               case 88:
               case 89:
               case 98:
               case 99:
               default:
                  break;
               case 7:
                  this.current = this.current.inverse();
                  break;
               case 8:
                  this.current = this.current.conceal();
                  break;
               case 9:
                  this.current = this.current.crossedOut();
                  break;
               case 22:
                  this.current = this.current.boldOff().faintOff();
                  break;
               case 23:
                  this.current = this.current.italicOff();
                  break;
               case 24:
                  this.current = this.current.underlineOff();
                  break;
               case 25:
                  this.current = this.current.blinkOff();
                  break;
               case 27:
                  this.current = this.current.inverseOff();
                  break;
               case 28:
                  this.current = this.current.concealOff();
                  break;
               case 29:
                  this.current = this.current.crossedOutOff();
                  break;
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
                  this.current = this.current.foreground(var8 - 30);
                  break;
               case 38:
               case 48:
                  if (var7 + 1 < var6.length) {
                     ++var7;
                     int var9 = Integer.parseInt(var6[var7]);
                     int var10;
                     if (var9 == 2) {
                        if (var7 + 3 < var6.length) {
                           ++var7;
                           var10 = Integer.parseInt(var6[var7]);
                           ++var7;
                           int var11 = Integer.parseInt(var6[var7]);
                           ++var7;
                           int var12 = Integer.parseInt(var6[var7]);
                           int var13 = 16 + (var10 >> 3) * 36 + (var11 >> 3) * 6 + (var12 >> 3);
                           if (var8 == 38) {
                              this.current = this.current.foreground(var13);
                           } else {
                              this.current = this.current.background(var13);
                           }
                        }
                     } else if (var9 == 5 && var7 + 1 < var6.length) {
                        ++var7;
                        var10 = Integer.parseInt(var6[var7]);
                        if (var8 == 38) {
                           this.current = this.current.foreground(var10);
                        } else {
                           this.current = this.current.background(var10);
                        }
                     }
                  }
                  break;
               case 39:
                  this.current = this.current.foregroundOff();
                  break;
               case 40:
               case 41:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 47:
                  this.current = this.current.background(var8 - 40);
                  break;
               case 49:
                  this.current = this.current.backgroundOff();
                  break;
               case 90:
               case 91:
               case 92:
               case 93:
               case 94:
               case 95:
               case 96:
               case 97:
                  this.current = this.current.foreground(var8 - 90 + 8);
                  break;
               case 100:
               case 101:
               case 102:
               case 103:
               case 104:
               case 105:
               case 106:
               case 107:
                  this.current = this.current.background(var8 - 100 + 8);
               }
            }

            var3 = 0;
         }
      }

      return this;
   }

   protected void insertTab(AttributedStyle var1) {
      int var2 = this.tabs - this.lastLineLength % this.tabs;
      this.ensureCapacity(this.length + var2);

      for(int var3 = 0; var3 < var2; ++var3) {
         this.buffer[this.length] = ' ';
         this.style[this.length] = var1.getStyle();
         ++this.length;
      }

      this.lastLineLength += var2;
   }

   public void setLength(int var1) {
      this.length = var1;
   }

   public AttributedStringBuilder tabs(int var1) {
      if (this.length > 0) {
         throw new IllegalStateException("Cannot change tab size after appending text");
      } else if (var1 < 0) {
         throw new IllegalArgumentException("Tab size must be non negative");
      } else {
         this.tabs = var1;
         return this;
      }
   }

   public AttributedStringBuilder styleMatches(Pattern var1, AttributedStyle var2) {
      Matcher var3 = var1.matcher(this);

      while(var3.find()) {
         for(int var4 = var3.start(); var4 < var3.end(); ++var4) {
            this.style[var4] = this.style[var4] & ~var2.getMask() | var2.getStyle();
         }
      }

      return this;
   }

   public AttributedStringBuilder styleMatches(Pattern var1, List var2) {
      Matcher var3 = var1.matcher(this);

      while(var3.find()) {
         for(int var4 = 0; var4 < var3.groupCount(); ++var4) {
            AttributedStyle var5 = (AttributedStyle)var2.get(var4);

            for(int var6 = var3.start(var4 + 1); var6 < var3.end(var4 + 1); ++var6) {
               this.style[var6] = this.style[var6] & ~var5.getMask() | var5.getStyle();
            }
         }
      }

      return this;
   }

   public CharSequence subSequence(int var1, int var2) {
      return this.subSequence(var1, var2);
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

   private static void lambda$styled$2(CharSequence var0, AttributedStringBuilder var1) {
      var1.append(var0);
   }

   private static AttributedStyle lambda$styled$1(AttributedStyle var0, AttributedStyle var1) {
      return var0;
   }

   private static void lambda$styled$0(CharSequence var0, AttributedStringBuilder var1) {
      var1.append(var0);
   }
}
