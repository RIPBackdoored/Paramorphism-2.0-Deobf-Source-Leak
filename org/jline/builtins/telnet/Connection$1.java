package org.jline.builtins.telnet;

class Connection$1 {
   static final int[] $SwitchMap$org$jline$builtins$telnet$ConnectionEvent$Type = new int[ConnectionEvent$Type.values().length];

   static {
      try {
         $SwitchMap$org$jline$builtins$telnet$ConnectionEvent$Type[ConnectionEvent$Type.CONNECTION_IDLE.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
      }

      try {
         $SwitchMap$org$jline$builtins$telnet$ConnectionEvent$Type[ConnectionEvent$Type.CONNECTION_TIMEDOUT.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$org$jline$builtins$telnet$ConnectionEvent$Type[ConnectionEvent$Type.CONNECTION_LOGOUTREQUEST.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$jline$builtins$telnet$ConnectionEvent$Type[ConnectionEvent$Type.CONNECTION_BREAK.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$jline$builtins$telnet$ConnectionEvent$Type[ConnectionEvent$Type.CONNECTION_TERMINAL_GEOMETRY_CHANGED.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
      }

   }
}
