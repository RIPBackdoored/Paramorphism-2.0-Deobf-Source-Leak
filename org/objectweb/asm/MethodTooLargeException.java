package org.objectweb.asm;

public final class MethodTooLargeException extends IndexOutOfBoundsException {
   private static final long serialVersionUID = 6807380416709738314L;
   private final String className;
   private final String methodName;
   private final String descriptor;
   private final int codeSize;

   public MethodTooLargeException(String var1, String var2, String var3, int var4) {
      super("Method too large: " + var1 + "." + var2 + " " + var3);
      this.className = var1;
      this.methodName = var2;
      this.descriptor = var3;
      this.codeSize = var4;
   }

   public String getClassName() {
      return this.className;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public int getCodeSize() {
      return this.codeSize;
   }
}
