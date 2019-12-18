package org.jline.utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

public class AnsiWriter extends FilterWriter {
   private static final char[] RESET_CODE = "\u001b[0m".toCharArray();
   private static final int MAX_ESCAPE_SEQUENCE_LENGTH = 100;
   private final char[] buffer = new char[100];
   private int pos = 0;
   private int startOfValue;
   private final ArrayList options = new ArrayList();
   private static final int LOOKING_FOR_FIRST_ESC_CHAR = 0;
   private static final int LOOKING_FOR_SECOND_ESC_CHAR = 1;
   private static final int LOOKING_FOR_NEXT_ARG = 2;
   private static final int LOOKING_FOR_STR_ARG_END = 3;
   private static final int LOOKING_FOR_INT_ARG_END = 4;
   private static final int LOOKING_FOR_OSC_COMMAND = 5;
   private static final int LOOKING_FOR_OSC_COMMAND_END = 6;
   private static final int LOOKING_FOR_OSC_PARAM = 7;
   private static final int LOOKING_FOR_ST = 8;
   private static final int LOOKING_FOR_CHARSET = 9;
   int state = 0;
   private static final int FIRST_ESC_CHAR = 27;
   private static final int SECOND_ESC_CHAR = 91;
   private static final int SECOND_OSC_CHAR = 93;
   private static final int BEL = 7;
   private static final int SECOND_ST_CHAR = 92;
   private static final int SECOND_CHARSET0_CHAR = 40;
   private static final int SECOND_CHARSET1_CHAR = 41;
   protected static final int ERASE_SCREEN_TO_END = 0;
   protected static final int ERASE_SCREEN_TO_BEGINING = 1;
   protected static final int ERASE_SCREEN = 2;
   protected static final int ERASE_LINE_TO_END = 0;
   protected static final int ERASE_LINE_TO_BEGINING = 1;
   protected static final int ERASE_LINE = 2;
   protected static final int ATTRIBUTE_INTENSITY_BOLD = 1;
   protected static final int ATTRIBUTE_INTENSITY_FAINT = 2;
   protected static final int ATTRIBUTE_ITALIC = 3;
   protected static final int ATTRIBUTE_UNDERLINE = 4;
   protected static final int ATTRIBUTE_BLINK_SLOW = 5;
   protected static final int ATTRIBUTE_BLINK_FAST = 6;
   protected static final int ATTRIBUTE_NEGATIVE_ON = 7;
   protected static final int ATTRIBUTE_CONCEAL_ON = 8;
   protected static final int ATTRIBUTE_UNDERLINE_DOUBLE = 21;
   protected static final int ATTRIBUTE_INTENSITY_NORMAL = 22;
   protected static final int ATTRIBUTE_UNDERLINE_OFF = 24;
   protected static final int ATTRIBUTE_BLINK_OFF = 25;
   /** @deprecated */
   @Deprecated
   protected static final int ATTRIBUTE_NEGATIVE_Off = 27;
   protected static final int ATTRIBUTE_NEGATIVE_OFF = 27;
   protected static final int ATTRIBUTE_CONCEAL_OFF = 28;
   protected static final int BLACK = 0;
   protected static final int RED = 1;
   protected static final int GREEN = 2;
   protected static final int YELLOW = 3;
   protected static final int BLUE = 4;
   protected static final int MAGENTA = 5;
   protected static final int CYAN = 6;
   protected static final int WHITE = 7;

   public AnsiWriter(Writer var1) {
      super(var1);
   }

   public synchronized void write(int var1) throws IOException {
      Integer var33;
      String var2;
      boolean var3;
      switch(this.state) {
      case 0:
         if (var1 == 27) {
            this.buffer[this.pos++] = (char)var1;
            this.state = 1;
         } else {
            this.out.write(var1);
         }
         break;
      case 1:
         this.buffer[this.pos++] = (char)var1;
         if (var1 == 91) {
            this.state = 2;
         } else if (var1 == 93) {
            this.state = 5;
         } else if (var1 == 40) {
            this.options.add(48);
            this.state = 9;
         } else if (var1 == 41) {
            this.options.add(49);
            this.state = 9;
         } else {
            this.reset(false);
         }
         break;
      case 2:
         this.buffer[this.pos++] = (char)var1;
         if (34 == var1) {
            this.startOfValue = this.pos - 1;
            this.state = 3;
         } else if (48 <= var1 && var1 <= 57) {
            this.startOfValue = this.pos - 1;
            this.state = 4;
         } else if (59 == var1) {
            this.options.add((Object)null);
         } else if (63 == var1) {
            this.options.add('?');
         } else if (61 == var1) {
            this.options.add('=');
         } else {
            boolean var32 = true;
            boolean var27 = false;

            try {
               var27 = true;
               var32 = this.processEscapeCommand(this.options, var1);
               var27 = false;
            } finally {
               if (var27) {
                  this.reset(var32);
               }
            }

            this.reset(var32);
         }
         break;
      case 3:
         this.buffer[this.pos++] = (char)var1;
         if (34 != var1) {
            var2 = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
            this.options.add(var2);
            if (var1 == 59) {
               this.state = 2;
            } else {
               this.reset(this.processEscapeCommand(this.options, var1));
            }
         }
         break;
      case 4:
         this.buffer[this.pos++] = (char)var1;
         if (48 > var1 || var1 > 57) {
            var2 = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
            var33 = Integer.valueOf(var2);
            this.options.add(var33);
            if (var1 == 59) {
               this.state = 2;
            } else {
               boolean var4 = true;
               boolean var22 = false;

               try {
                  var22 = true;
                  var4 = this.processEscapeCommand(this.options, var1);
                  var22 = false;
               } finally {
                  if (var22) {
                     this.reset(var4);
                  }
               }

               this.reset(var4);
            }
         }
         break;
      case 5:
         this.buffer[this.pos++] = (char)var1;
         if (48 <= var1 && var1 <= 57) {
            this.startOfValue = this.pos - 1;
            this.state = 6;
         } else {
            this.reset(false);
         }
         break;
      case 6:
         this.buffer[this.pos++] = (char)var1;
         if (59 == var1) {
            var2 = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
            var33 = Integer.valueOf(var2);
            this.options.add(var33);
            this.startOfValue = this.pos;
            this.state = 7;
         } else if (48 > var1 || var1 > 57) {
            this.reset(false);
         }
         break;
      case 7:
         this.buffer[this.pos++] = (char)var1;
         if (7 == var1) {
            var2 = new String(this.buffer, this.startOfValue, this.pos - 1 - this.startOfValue);
            this.options.add(var2);
            var3 = true;
            boolean var12 = false;

            try {
               var12 = true;
               var3 = this.processOperatingSystemCommand(this.options);
               var12 = false;
            } finally {
               if (var12) {
                  this.reset(var3);
               }
            }

            this.reset(var3);
         } else if (27 == var1) {
            this.state = 8;
         }
         break;
      case 8:
         this.buffer[this.pos++] = (char)var1;
         if (92 == var1) {
            var2 = new String(this.buffer, this.startOfValue, this.pos - 2 - this.startOfValue);
            this.options.add(var2);
            var3 = true;
            boolean var17 = false;

            try {
               var17 = true;
               var3 = this.processOperatingSystemCommand(this.options);
               var17 = false;
            } finally {
               if (var17) {
                  this.reset(var3);
               }
            }

            this.reset(var3);
         } else {
            this.state = 7;
         }
         break;
      case 9:
         this.options.add((char)var1);
         this.reset(this.processCharsetSelect(this.options));
      }

      if (this.pos >= this.buffer.length) {
         this.reset(false);
      }

   }

   private void reset(boolean var1) throws IOException {
      if (!var1) {
         this.out.write(this.buffer, 0, this.pos);
      }

      this.pos = 0;
      this.startOfValue = 0;
      this.options.clear();
      this.state = 0;
   }

   private int getNextOptionInt(Iterator var1) throws IOException {
      Object var2;
      do {
         if (!var1.hasNext()) {
            throw new IllegalArgumentException();
         }

         var2 = var1.next();
      } while(var2 == null);

      return (Integer)var2;
   }

   private boolean processEscapeCommand(ArrayList var1, int var2) throws IOException {
      try {
         boolean var10000;
         switch(var2) {
         case 65:
            this.processCursorUp(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 66:
            this.processCursorDown(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 67:
            this.processCursorRight(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 68:
            this.processCursorLeft(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 69:
            this.processCursorDownLine(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 70:
            this.processCursorUpLine(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 71:
            this.processCursorToColumn(this.optionInt(var1, 0));
            var10000 = true;
            return var10000;
         case 72:
         case 102:
            this.processCursorTo(this.optionInt(var1, 0, 1), this.optionInt(var1, 1, 1));
            var10000 = true;
            return var10000;
         case 73:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 116:
         default:
            if (97 <= var2 && 122 <= var2) {
               this.processUnknownExtension(var1, var2);
               var10000 = true;
               return var10000;
            } else {
               if (65 <= var2 && 90 <= var2) {
                  this.processUnknownExtension(var1, var2);
                  var10000 = true;
                  return var10000;
               }

               var10000 = false;
               return var10000;
            }
         case 74:
            this.processEraseScreen(this.optionInt(var1, 0, 0));
            var10000 = true;
            return var10000;
         case 75:
            this.processEraseLine(this.optionInt(var1, 0, 0));
            var10000 = true;
            return var10000;
         case 76:
            this.processInsertLine(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 77:
            this.processDeleteLine(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 83:
            this.processScrollUp(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 84:
            this.processScrollDown(this.optionInt(var1, 0, 1));
            var10000 = true;
            return var10000;
         case 109:
            Iterator var3 = var1.iterator();

            while(var3.hasNext()) {
               Object var4 = var3.next();
               if (var4 != null && var4.getClass() != Integer.class) {
                  throw new IllegalArgumentException();
               }
            }

            int var12 = 0;
            Iterator var13 = var1.iterator();

            while(var13.hasNext()) {
               Object var5 = var13.next();
               if (var5 != null) {
                  ++var12;
                  int var6 = (Integer)var5;
                  if (30 <= var6 && var6 <= 37) {
                     this.processSetForegroundColor(var6 - 30);
                  } else if (40 <= var6 && var6 <= 47) {
                     this.processSetBackgroundColor(var6 - 40);
                  } else if (90 <= var6 && var6 <= 97) {
                     this.processSetForegroundColor(var6 - 90, true);
                  } else if (100 <= var6 && var6 <= 107) {
                     this.processSetBackgroundColor(var6 - 100, true);
                  } else if (var6 != 38 && var6 != 48) {
                     switch(var6) {
                     case 0:
                        this.processAttributeRest();
                        break;
                     case 39:
                        this.processDefaultTextColor();
                        break;
                     case 49:
                        this.processDefaultBackgroundColor();
                        break;
                     default:
                        this.processSetAttribute(var6);
                     }
                  } else {
                     int var7 = this.getNextOptionInt(var13);
                     int var8;
                     if (var7 == 2) {
                        var8 = this.getNextOptionInt(var13);
                        int var9 = this.getNextOptionInt(var13);
                        int var10 = this.getNextOptionInt(var13);
                        if (var8 < 0 || var8 > 255 || var9 < 0 || var9 > 255 || var10 < 0 || var10 > 255) {
                           throw new IllegalArgumentException();
                        }

                        if (var6 == 38) {
                           this.processSetForegroundColorExt(var8, var9, var10);
                        } else {
                           this.processSetBackgroundColorExt(var8, var9, var10);
                        }
                     } else {
                        if (var7 != 5) {
                           throw new IllegalArgumentException();
                        }

                        var8 = this.getNextOptionInt(var13);
                        if (var8 < 0 || var8 > 255) {
                           throw new IllegalArgumentException();
                        }

                        if (var6 == 38) {
                           this.processSetForegroundColorExt(var8);
                        } else {
                           this.processSetBackgroundColorExt(var8);
                        }
                     }
                  }
               }
            }

            if (var12 == 0) {
               this.processAttributeRest();
            }

            var10000 = true;
            return var10000;
         case 115:
            this.processSaveCursorPosition();
            var10000 = true;
            return var10000;
         case 117:
            this.processRestoreCursorPosition();
            var10000 = true;
            return var10000;
         }
      } catch (IllegalArgumentException var11) {
         return false;
      }
   }

   private boolean processOperatingSystemCommand(ArrayList var1) throws IOException {
      int var2 = this.optionInt(var1, 0);
      String var3 = (String)var1.get(1);

      boolean var10000;
      try {
         switch(var2) {
         case 0:
            this.processChangeIconNameAndWindowTitle(var3);
            var10000 = true;
            return var10000;
         case 1:
            this.processChangeIconName(var3);
            var10000 = true;
            return var10000;
         case 2:
            this.processChangeWindowTitle(var3);
            var10000 = true;
            return var10000;
         default:
            this.processUnknownOperatingSystemCommand(var2, var3);
            var10000 = true;
         }
      } catch (IllegalArgumentException var5) {
         return false;
      }

      return var10000;
   }

   protected void processRestoreCursorPosition() throws IOException {
   }

   protected void processSaveCursorPosition() throws IOException {
   }

   protected void processInsertLine(int var1) throws IOException {
   }

   protected void processDeleteLine(int var1) throws IOException {
   }

   protected void processScrollDown(int var1) throws IOException {
   }

   protected void processScrollUp(int var1) throws IOException {
   }

   protected void processEraseScreen(int var1) throws IOException {
   }

   protected void processEraseLine(int var1) throws IOException {
   }

   protected void processSetAttribute(int var1) throws IOException {
   }

   protected void processSetForegroundColor(int var1) throws IOException {
      this.processSetForegroundColor(var1, false);
   }

   protected void processSetForegroundColor(int var1, boolean var2) throws IOException {
      this.processSetForegroundColorExt(var2 ? var1 + 8 : var1);
   }

   protected void processSetForegroundColorExt(int var1) throws IOException {
   }

   protected void processSetForegroundColorExt(int var1, int var2, int var3) throws IOException {
      this.processSetForegroundColorExt(Colors.roundRgbColor(var1, var2, var3, 16));
   }

   protected void processSetBackgroundColor(int var1) throws IOException {
      this.processSetBackgroundColor(var1, false);
   }

   protected void processSetBackgroundColor(int var1, boolean var2) throws IOException {
      this.processSetBackgroundColorExt(var2 ? var1 + 8 : var1);
   }

   protected void processSetBackgroundColorExt(int var1) throws IOException {
   }

   protected void processSetBackgroundColorExt(int var1, int var2, int var3) throws IOException {
      this.processSetBackgroundColorExt(Colors.roundRgbColor(var1, var2, var3, 16));
   }

   protected void processDefaultTextColor() throws IOException {
   }

   protected void processDefaultBackgroundColor() throws IOException {
   }

   protected void processAttributeRest() throws IOException {
   }

   protected void processCursorTo(int var1, int var2) throws IOException {
   }

   protected void processCursorToColumn(int var1) throws IOException {
   }

   protected void processCursorUpLine(int var1) throws IOException {
   }

   protected void processCursorDownLine(int var1) throws IOException {
      for(int var2 = 0; var2 < var1; ++var2) {
         this.out.write(10);
      }

   }

   protected void processCursorLeft(int var1) throws IOException {
   }

   protected void processCursorRight(int var1) throws IOException {
      for(int var2 = 0; var2 < var1; ++var2) {
         this.out.write(32);
      }

   }

   protected void processCursorDown(int var1) throws IOException {
   }

   protected void processCursorUp(int var1) throws IOException {
   }

   protected void processUnknownExtension(ArrayList var1, int var2) {
   }

   protected void processChangeIconNameAndWindowTitle(String var1) {
      this.processChangeIconName(var1);
      this.processChangeWindowTitle(var1);
   }

   protected void processChangeIconName(String var1) {
   }

   protected void processChangeWindowTitle(String var1) {
   }

   protected void processUnknownOperatingSystemCommand(int var1, String var2) {
   }

   private boolean processCharsetSelect(ArrayList var1) throws IOException {
      int var2 = this.optionInt(var1, 0);
      char var3 = (Character)var1.get(1);
      this.processCharsetSelect(var2, var3);
      return true;
   }

   protected void processCharsetSelect(int var1, char var2) {
   }

   private int optionInt(ArrayList var1, int var2) {
      if (var1.size() <= var2) {
         throw new IllegalArgumentException();
      } else {
         Object var3 = var1.get(var2);
         if (var3 == null) {
            throw new IllegalArgumentException();
         } else if (!var3.getClass().equals(Integer.class)) {
            throw new IllegalArgumentException();
         } else {
            return (Integer)var3;
         }
      }
   }

   private int optionInt(ArrayList var1, int var2, int var3) {
      if (var1.size() > var2) {
         Object var4 = var1.get(var2);
         return var4 == null ? var3 : (Integer)var4;
      } else {
         return var3;
      }
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      for(int var4 = 0; var4 < var3; ++var4) {
         this.write(var1[var2 + var4]);
      }

   }

   public void write(String var1, int var2, int var3) throws IOException {
      for(int var4 = 0; var4 < var3; ++var4) {
         this.write(var1.charAt(var2 + var4));
      }

   }

   public void close() throws IOException {
      this.write(RESET_CODE);
      this.flush();
      super.close();
   }
}
