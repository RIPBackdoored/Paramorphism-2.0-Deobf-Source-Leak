package org.jline.terminal.impl;

import org.jline.terminal.Terminal$Signal;

class AbstractTerminal$1 {
   static final int[] $SwitchMap$org$jline$terminal$Terminal$Signal = new int[Terminal$Signal.values().length];

   static {
      try {
         $SwitchMap$org$jline$terminal$Terminal$Signal[Terminal$Signal.INT.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$jline$terminal$Terminal$Signal[Terminal$Signal.QUIT.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$jline$terminal$Terminal$Signal[Terminal$Signal.TSTP.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
