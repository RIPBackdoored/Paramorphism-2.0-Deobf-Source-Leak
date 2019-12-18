package org.jline.builtins;

class Nano$1 {
   static final int[] $SwitchMap$org$jline$builtins$Nano$Operation;
   static final int[] $SwitchMap$org$jline$builtins$Nano$WriteFormat;
   static final int[] $SwitchMap$org$jline$builtins$Nano$WriteMode = new int[Nano$WriteMode.values().length];

   static {
      try {
         $SwitchMap$org$jline$builtins$Nano$WriteMode[Nano$WriteMode.WRITE.ordinal()] = 1;
      } catch (NoSuchFieldError var55) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$WriteMode[Nano$WriteMode.APPEND.ordinal()] = 2;
      } catch (NoSuchFieldError var54) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$WriteMode[Nano$WriteMode.PREPEND.ordinal()] = 3;
      } catch (NoSuchFieldError var53) {
      }

      $SwitchMap$org$jline$builtins$Nano$WriteFormat = new int[Nano$WriteFormat.values().length];

      try {
         $SwitchMap$org$jline$builtins$Nano$WriteFormat[Nano$WriteFormat.UNIX.ordinal()] = 1;
      } catch (NoSuchFieldError var52) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$WriteFormat[Nano$WriteFormat.DOS.ordinal()] = 2;
      } catch (NoSuchFieldError var51) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$WriteFormat[Nano$WriteFormat.MAC.ordinal()] = 3;
      } catch (NoSuchFieldError var50) {
      }

      $SwitchMap$org$jline$builtins$Nano$Operation = new int[Nano$Operation.values().length];

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.QUIT.ordinal()] = 1;
      } catch (NoSuchFieldError var49) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.WRITE.ordinal()] = 2;
      } catch (NoSuchFieldError var48) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.READ.ordinal()] = 3;
      } catch (NoSuchFieldError var47) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.UP.ordinal()] = 4;
      } catch (NoSuchFieldError var46) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.DOWN.ordinal()] = 5;
      } catch (NoSuchFieldError var45) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.LEFT.ordinal()] = 6;
      } catch (NoSuchFieldError var44) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.RIGHT.ordinal()] = 7;
      } catch (NoSuchFieldError var43) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.INSERT.ordinal()] = 8;
      } catch (NoSuchFieldError var42) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.BACKSPACE.ordinal()] = 9;
      } catch (NoSuchFieldError var41) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.DELETE.ordinal()] = 10;
      } catch (NoSuchFieldError var40) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.WRAP.ordinal()] = 11;
      } catch (NoSuchFieldError var39) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.NUMBERS.ordinal()] = 12;
      } catch (NoSuchFieldError var38) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.SMOOTH_SCROLLING.ordinal()] = 13;
      } catch (NoSuchFieldError var37) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.MOUSE_SUPPORT.ordinal()] = 14;
      } catch (NoSuchFieldError var36) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.ONE_MORE_LINE.ordinal()] = 15;
      } catch (NoSuchFieldError var35) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.CLEAR_SCREEN.ordinal()] = 16;
      } catch (NoSuchFieldError var34) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.PREV_BUFFER.ordinal()] = 17;
      } catch (NoSuchFieldError var33) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.NEXT_BUFFER.ordinal()] = 18;
      } catch (NoSuchFieldError var32) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.CUR_POS.ordinal()] = 19;
      } catch (NoSuchFieldError var31) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.PREV_WORD.ordinal()] = 20;
      } catch (NoSuchFieldError var30) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.NEXT_WORD.ordinal()] = 21;
      } catch (NoSuchFieldError var29) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.BEGINNING_OF_LINE.ordinal()] = 22;
      } catch (NoSuchFieldError var28) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.END_OF_LINE.ordinal()] = 23;
      } catch (NoSuchFieldError var27) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.FIRST_LINE.ordinal()] = 24;
      } catch (NoSuchFieldError var26) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.LAST_LINE.ordinal()] = 25;
      } catch (NoSuchFieldError var25) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.PREV_PAGE.ordinal()] = 26;
      } catch (NoSuchFieldError var24) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.NEXT_PAGE.ordinal()] = 27;
      } catch (NoSuchFieldError var23) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.SCROLL_UP.ordinal()] = 28;
      } catch (NoSuchFieldError var22) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.SCROLL_DOWN.ordinal()] = 29;
      } catch (NoSuchFieldError var21) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.SEARCH.ordinal()] = 30;
      } catch (NoSuchFieldError var20) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.NEXT_SEARCH.ordinal()] = 31;
      } catch (NoSuchFieldError var19) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.HELP.ordinal()] = 32;
      } catch (NoSuchFieldError var18) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.CONSTANT_CURSOR.ordinal()] = 33;
      } catch (NoSuchFieldError var17) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.VERBATIM.ordinal()] = 34;
      } catch (NoSuchFieldError var16) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.MATCHING.ordinal()] = 35;
      } catch (NoSuchFieldError var15) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.MOUSE_EVENT.ordinal()] = 36;
      } catch (NoSuchFieldError var14) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.CANCEL.ordinal()] = 37;
      } catch (NoSuchFieldError var13) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.ACCEPT.ordinal()] = 38;
      } catch (NoSuchFieldError var12) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.MAC_FORMAT.ordinal()] = 39;
      } catch (NoSuchFieldError var11) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.DOS_FORMAT.ordinal()] = 40;
      } catch (NoSuchFieldError var10) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.APPEND_MODE.ordinal()] = 41;
      } catch (NoSuchFieldError var9) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.PREPEND_MODE.ordinal()] = 42;
      } catch (NoSuchFieldError var8) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.BACKUP.ordinal()] = 43;
      } catch (NoSuchFieldError var7) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.NEW_BUFFER.ordinal()] = 44;
      } catch (NoSuchFieldError var6) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.CASE_SENSITIVE.ordinal()] = 45;
      } catch (NoSuchFieldError var5) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.BACKWARDS.ordinal()] = 46;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.REGEXP.ordinal()] = 47;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.NO.ordinal()] = 48;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$jline$builtins$Nano$Operation[Nano$Operation.YES.ordinal()] = 49;
      } catch (NoSuchFieldError var1) {
      }

   }
}
