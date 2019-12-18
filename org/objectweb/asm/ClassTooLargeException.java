package org.objectweb.asm;

public final class ClassTooLargeException extends IndexOutOfBoundsException {
   private static final long serialVersionUID = 160715609518896765L;
   private final String className;
   private final int constantPoolCount;

   public ClassTooLargeException(String var1, int var2) {
      super("Class too large: " + var1);
      this.className = var1;
      this.constantPoolCount = var2;
   }

   public String getClassName() {
      return this.className;
   }

   public int getConstantPoolCount() {
      return this.constantPoolCount;
   }
}
