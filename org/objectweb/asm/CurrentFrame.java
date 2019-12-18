package org.objectweb.asm;

final class CurrentFrame extends Frame {
   CurrentFrame(Label var1) {
      super(var1);
   }

   void execute(int var1, int var2, Symbol var3, SymbolTable var4) {
      super.execute(var1, var2, var3, var4);
      Frame var5 = new Frame((Label)null);
      this.merge(var4, var5, 0);
      this.copyFrom(var5);
   }
}
