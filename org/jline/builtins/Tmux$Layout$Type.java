package org.jline.builtins;

final class Tmux$Layout$Type extends Enum {
   public static final Tmux$Layout$Type LeftRight = new Tmux$Layout$Type("LeftRight", 0);
   public static final Tmux$Layout$Type TopBottom = new Tmux$Layout$Type("TopBottom", 1);
   public static final Tmux$Layout$Type WindowPane = new Tmux$Layout$Type("WindowPane", 2);
   private static final Tmux$Layout$Type[] $VALUES = new Tmux$Layout$Type[]{LeftRight, TopBottom, WindowPane};

   public static Tmux$Layout$Type[] values() {
      return (Tmux$Layout$Type[])$VALUES.clone();
   }

   public static Tmux$Layout$Type valueOf(String var0) {
      return (Tmux$Layout$Type)Enum.valueOf(Tmux$Layout$Type.class, var0);
   }

   private Tmux$Layout$Type(String var1, int var2) {
      super(var1, var2);
   }
}
