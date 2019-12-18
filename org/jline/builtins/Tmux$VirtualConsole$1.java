package org.jline.builtins;

class Tmux$VirtualConsole$1 extends ScreenTerminal {
   final Runnable val$dirty;
   final Tmux$VirtualConsole this$0;

   Tmux$VirtualConsole$1(Tmux$VirtualConsole var1, int var2, int var3, Runnable var4) {
      super(var2, var3);
      this.this$0 = var1;
      this.val$dirty = var4;
   }

   protected void setDirty() {
      super.setDirty();
      this.val$dirty.run();
   }
}
