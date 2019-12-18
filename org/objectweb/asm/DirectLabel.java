package org.objectweb.asm;

public class DirectLabel extends Label {
   public DirectLabel(int var1) {
      super();
      this.bytecodeOffset = var1;
      this.flags = (short)(this.flags | 4);
   }
}
