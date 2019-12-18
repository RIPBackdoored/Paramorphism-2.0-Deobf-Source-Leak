package org.jline.builtins;

final class Tmux$Binding extends Enum {
   public static final Tmux$Binding Discard = new Tmux$Binding("Discard", 0);
   public static final Tmux$Binding SelfInsert = new Tmux$Binding("SelfInsert", 1);
   public static final Tmux$Binding Mouse = new Tmux$Binding("Mouse", 2);
   private static final Tmux$Binding[] $VALUES = new Tmux$Binding[]{Discard, SelfInsert, Mouse};

   public static Tmux$Binding[] values() {
      return (Tmux$Binding[])$VALUES.clone();
   }

   public static Tmux$Binding valueOf(String var0) {
      return (Tmux$Binding)Enum.valueOf(Tmux$Binding.class, var0);
   }

   private Tmux$Binding(String var1, int var2) {
      super(var1, var2);
   }
}
