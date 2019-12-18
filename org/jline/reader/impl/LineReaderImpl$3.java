package org.jline.reader.impl;

class LineReaderImpl$3 {
   static final int[] $SwitchMap$org$jline$reader$impl$LineReaderImpl$State = new int[LineReaderImpl$State.values().length];

   static {
      try {
         $SwitchMap$org$jline$reader$impl$LineReaderImpl$State[LineReaderImpl$State.DONE.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$jline$reader$impl$LineReaderImpl$State[LineReaderImpl$State.EOF.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$jline$reader$impl$LineReaderImpl$State[LineReaderImpl$State.INTERRUPT.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
