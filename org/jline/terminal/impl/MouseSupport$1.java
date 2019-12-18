package org.jline.terminal.impl;

import org.jline.terminal.Terminal$MouseTracking;

class MouseSupport$1 {
   static final int[] $SwitchMap$org$jline$terminal$Terminal$MouseTracking = new int[Terminal$MouseTracking.values().length];

   static {
      try {
         $SwitchMap$org$jline$terminal$Terminal$MouseTracking[Terminal$MouseTracking.Off.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$org$jline$terminal$Terminal$MouseTracking[Terminal$MouseTracking.Normal.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$jline$terminal$Terminal$MouseTracking[Terminal$MouseTracking.Button.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$jline$terminal$Terminal$MouseTracking[Terminal$MouseTracking.Any.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }

   }
}
