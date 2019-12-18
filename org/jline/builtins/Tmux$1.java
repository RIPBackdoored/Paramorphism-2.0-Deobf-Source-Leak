package org.jline.builtins;

class Tmux$1 {
   static final int[] $SwitchMap$org$jline$builtins$Tmux$Layout$Type = new int[Tmux$Layout$Type.values().length];

   static {
      try {
         $SwitchMap$org$jline$builtins$Tmux$Layout$Type[Tmux$Layout$Type.WindowPane.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$jline$builtins$Tmux$Layout$Type[Tmux$Layout$Type.TopBottom.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$jline$builtins$Tmux$Layout$Type[Tmux$Layout$Type.LeftRight.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
