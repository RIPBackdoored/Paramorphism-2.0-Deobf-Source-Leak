package org.jline.utils;

import java.util.ArrayList;
import java.util.List;
import org.jline.terminal.Terminal;

public abstract class AttributedCharSequence implements CharSequence {
   static final boolean DISABLE_ALTERNATE_CHARSET = Boolean.getBoolean("org.jline.utils.disableAlternateCharset");

   public AttributedCharSequence() {
      super();
   }

   public void print(Terminal var1) {
      var1.writer().print(this.toAnsi(var1));
   }

   public void println(Terminal var1) {
      var1.writer().println(this.toAnsi(var1));
   }

   public String toAnsi() {
      return this.toAnsi((Terminal)null);
   }

   public String toAnsi(Terminal var1) {
      if (var1 != null && "dumb".equals(var1.getType())) {
         return this.toString();
      } else {
         int var2 = 256;
         boolean var3 = false;
         String var4 = null;
         String var5 = null;
         if (var1 != null) {
            Integer var6 = var1.getNumericCapability(InfoCmp$Capability.max_colors);
            if (var6 != null) {
               var2 = var6;
            }

            var3 = "windows-256color".equals(var1.getType()) || "windows-conemu".equals(var1.getType());
            if (!DISABLE_ALTERNATE_CHARSET) {
               var4 = Curses.tputs(var1.getStringCapability(InfoCmp$Capability.enter_alt_charset_mode));
               var5 = Curses.tputs(var1.getStringCapability(InfoCmp$Capability.exit_alt_charset_mode));
            }
         }

         return this.toAnsi(var2, var3, var4, var5);
      }
   }

   public String toAnsi(int var1, boolean var2) {
      return this.toAnsi(var1, var2, (String)null, (String)null);
   }

   public String toAnsi(int var1, boolean var2, String var3, String var4) {
      StringBuilder var5 = new StringBuilder();
      int var6 = 0;
      int var7 = -1;
      int var8 = -1;
      boolean var9 = false;

      for(int var10 = 0; var10 < this.length(); ++var10) {
         char var11 = this.charAt(var10);
         if (var3 != null && var4 != null) {
            char var12 = var11;
            switch(var11) {
            case '─':
               var11 = 'q';
               break;
            case '│':
               var11 = 'x';
               break;
            case '┌':
               var11 = 'l';
               break;
            case '┐':
               var11 = 'k';
               break;
            case '└':
               var11 = 'm';
               break;
            case '┘':
               var11 = 'j';
               break;
            case '├':
               var11 = 't';
               break;
            case '┤':
               var11 = 'u';
               break;
            case '┬':
               var11 = 'w';
               break;
            case '┴':
               var11 = 'v';
               break;
            case '┼':
               var11 = 'n';
            }

            boolean var13 = var9;
            var9 = var11 != var12;
            if (var13 ^ var9) {
               var5.append(var9 ? var3 : var4);
            }
         }

         int var18 = this.styleCodeAt(var10) & -1025;
         if (var6 != var18) {
            int var19 = (var6 ^ var18) & 2047;
            int var14 = (var18 & 256) != 0 ? (var18 & 16711680) >>> 16 : -1;
            int var15 = (var18 & 512) != 0 ? (var18 & -16777216) >>> 24 : -1;
            if (var18 == 0) {
               var5.append("\u001b[0m");
               var8 = -1;
               var7 = -1;
            } else {
               var5.append("\u001b[");
               boolean var16 = true;
               if ((var19 & 4) != 0) {
                  var16 = attr(var5, (var18 & 4) != 0 ? "3" : "23", var16);
               }

               if ((var19 & 8) != 0) {
                  var16 = attr(var5, (var18 & 8) != 0 ? "4" : "24", var16);
               }

               if ((var19 & 16) != 0) {
                  var16 = attr(var5, (var18 & 16) != 0 ? "5" : "25", var16);
               }

               if ((var19 & 32) != 0) {
                  var16 = attr(var5, (var18 & 32) != 0 ? "7" : "27", var16);
               }

               if ((var19 & 64) != 0) {
                  var16 = attr(var5, (var18 & 64) != 0 ? "8" : "28", var16);
               }

               if ((var19 & 128) != 0) {
                  var16 = attr(var5, (var18 & 128) != 0 ? "9" : "29", var16);
               }

               int var17;
               if (var7 != var14) {
                  if (var14 >= 0) {
                     var17 = Colors.roundColor(var14, var1);
                     if (var17 < 8 && !var2) {
                        var16 = attr(var5, "3" + Integer.toString(var17), var16);
                        var19 |= var18 & 1;
                     } else if (var17 < 16 && !var2) {
                        var16 = attr(var5, "9" + Integer.toString(var17 - 8), var16);
                        var19 |= var18 & 1;
                     } else {
                        var16 = attr(var5, "38;5;" + Integer.toString(var17), var16);
                     }
                  } else {
                     var16 = attr(var5, "39", var16);
                  }

                  var7 = var14;
               }

               if (var8 != var15) {
                  if (var15 >= 0) {
                     var17 = Colors.roundColor(var15, var1);
                     if (var17 < 8 && !var2) {
                        var16 = attr(var5, "4" + Integer.toString(var17), var16);
                     } else if (var17 < 16 && !var2) {
                        var16 = attr(var5, "10" + Integer.toString(var17 - 8), var16);
                     } else {
                        var16 = attr(var5, "48;5;" + Integer.toString(var17), var16);
                     }
                  } else {
                     var16 = attr(var5, "49", var16);
                  }

                  var8 = var15;
               }

               if ((var19 & 3) != 0) {
                  if ((var19 & 1) != 0 && (var18 & 1) == 0 || (var19 & 2) != 0 && (var18 & 2) == 0) {
                     var16 = attr(var5, "22", var16);
                  }

                  if ((var19 & 1) != 0 && (var18 & 1) != 0) {
                     var16 = attr(var5, "1", var16);
                  }

                  if ((var19 & 2) != 0 && (var18 & 2) != 0) {
                     attr(var5, "2", var16);
                  }
               }

               var5.append("m");
            }

            var6 = var18;
         }

         var5.append(var11);
      }

      if (var9) {
         var5.append(var4);
      }

      if (var6 != 0) {
         var5.append("\u001b[0m");
      }

      return var5.toString();
   }

   /** @deprecated */
   @Deprecated
   public static int rgbColor(int var0) {
      return Colors.rgbColor(var0);
   }

   /** @deprecated */
   @Deprecated
   public static int roundColor(int var0, int var1) {
      return Colors.roundColor(var0, var1);
   }

   /** @deprecated */
   @Deprecated
   public static int roundRgbColor(int var0, int var1, int var2, int var3) {
      return Colors.roundRgbColor(var0, var1, var2, var3);
   }

   private static boolean attr(StringBuilder var0, String var1, boolean var2) {
      if (!var2) {
         var0.append(";");
      }

      var0.append(var1);
      return false;
   }

   public abstract AttributedStyle styleAt(int var1);

   int styleCodeAt(int var1) {
      return this.styleAt(var1).getStyle();
   }

   public boolean isHidden(int var1) {
      return (this.styleCodeAt(var1) & 1024) != 0;
   }

   public int runStart(int var1) {
      for(AttributedStyle var2 = this.styleAt(var1); var1 > 0 && this.styleAt(var1 - 1).equals(var2); --var1) {
      }

      return var1;
   }

   public int runLimit(int var1) {
      for(AttributedStyle var2 = this.styleAt(var1); var1 < this.length() - 1 && this.styleAt(var1 + 1).equals(var2); ++var1) {
      }

      return var1 + 1;
   }

   public abstract AttributedString subSequence(int var1, int var2);

   public AttributedString substring(int var1, int var2) {
      return this.subSequence(var1, var2);
   }

   protected abstract char[] buffer();

   protected abstract int offset();

   public char charAt(int var1) {
      return this.buffer()[this.offset() + var1];
   }

   public int codePointAt(int var1) {
      return Character.codePointAt(this.buffer(), var1 + this.offset());
   }

   public boolean contains(char var1) {
      for(int var2 = 0; var2 < this.length(); ++var2) {
         if (this.charAt(var2) == var1) {
            return true;
         }
      }

      return false;
   }

   public int codePointBefore(int var1) {
      return Character.codePointBefore(this.buffer(), var1 + this.offset());
   }

   public int codePointCount(int var1, int var2) {
      return Character.codePointCount(this.buffer(), var1 + this.offset(), var2);
   }

   public int columnLength() {
      int var1 = 0;
      int var2 = this.length();

      int var4;
      for(int var3 = 0; var3 < var2; var3 += Character.charCount(var4)) {
         var4 = this.codePointAt(var3);
         if (!this.isHidden(var3)) {
            var1 += WCWidth.wcwidth(var4);
         }
      }

      return var1;
   }

   public AttributedString columnSubSequence(int var1, int var2) {
      int var3 = 0;

      int var4;
      int var5;
      int var6;
      for(var4 = 0; var3 < this.length(); var4 += var6) {
         var5 = this.codePointAt(var3);
         var6 = this.isHidden(var3) ? 0 : WCWidth.wcwidth(var5);
         if (var4 + var6 > var1) {
            break;
         }

         var3 += Character.charCount(var5);
      }

      int var7;
      for(var5 = var3; var5 < this.length(); var4 += var7) {
         var6 = this.codePointAt(var5);
         if (var6 == 10) {
            break;
         }

         var7 = this.isHidden(var5) ? 0 : WCWidth.wcwidth(var6);
         if (var4 + var7 > var2) {
            break;
         }

         var5 += Character.charCount(var6);
      }

      return this.subSequence(var3, var5);
   }

   public List columnSplitLength(int var1) {
      return this.columnSplitLength(var1, false, true);
   }

   public List columnSplitLength(int var1, boolean var2, boolean var3) {
      ArrayList var4 = new ArrayList();
      int var5 = 0;
      int var6 = var5;

      int var8;
      for(int var7 = 0; var5 < this.length(); var5 += Character.charCount(var8)) {
         var8 = this.codePointAt(var5);
         int var9 = this.isHidden(var5) ? 0 : WCWidth.wcwidth(var8);
         if (var8 == 10) {
            var4.add(this.subSequence(var6, var2 ? var5 + 1 : var5));
            var6 = var5 + 1;
            var7 = 0;
         } else if ((var7 += var9) > var1) {
            var4.add(this.subSequence(var6, var5));
            var6 = var5;
            var7 = var9;
         }
      }

      var4.add(this.subSequence(var6, var5));
      return var4;
   }

   public String toString() {
      return new String(this.buffer(), this.offset(), this.length());
   }

   public AttributedString toAttributedString() {
      return this.substring(0, this.length());
   }

   public CharSequence subSequence(int var1, int var2) {
      return this.subSequence(var1, var2);
   }
}
