package org.objectweb.asm;

import java.util.Arrays;

public final class ConstantDynamic {
   private final String name;
   private final String descriptor;
   private final Handle bootstrapMethod;
   private final Object[] bootstrapMethodArguments;

   public ConstantDynamic(String var1, String var2, Handle var3, Object... var4) {
      super();
      this.name = var1;
      this.descriptor = var2;
      this.bootstrapMethod = var3;
      this.bootstrapMethodArguments = var4;
   }

   public String getName() {
      return this.name;
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public Handle getBootstrapMethod() {
      return this.bootstrapMethod;
   }

   public int getBootstrapMethodArgumentCount() {
      return this.bootstrapMethodArguments.length;
   }

   public Object getBootstrapMethodArgument(int var1) {
      return this.bootstrapMethodArguments[var1];
   }

   Object[] getBootstrapMethodArgumentsUnsafe() {
      return this.bootstrapMethodArguments;
   }

   public int getSize() {
      char var1 = this.descriptor.charAt(0);
      return var1 != 'J' && var1 != 'D' ? 1 : 2;
   }

   void accept(MethodVisitor var1) {
      var1.visitInvokeDynamicInsn(this.name, this.descriptor, this.bootstrapMethod, this.bootstrapMethodArguments);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ConstantDynamic)) {
         return false;
      } else {
         ConstantDynamic var2 = (ConstantDynamic)var1;
         return this.name.equals(var2.name) && this.descriptor.equals(var2.descriptor) && this.bootstrapMethod.equals(var2.bootstrapMethod) && Arrays.equals(this.bootstrapMethodArguments, var2.bootstrapMethodArguments);
      }
   }

   public int hashCode() {
      return this.name.hashCode() ^ Integer.rotateLeft(this.descriptor.hashCode(), 8) ^ Integer.rotateLeft(this.bootstrapMethod.hashCode(), 16) ^ Integer.rotateLeft(Arrays.hashCode(this.bootstrapMethodArguments), 24);
   }

   public String toString() {
      return this.name + " : " + this.descriptor + ' ' + this.bootstrapMethod + ' ' + Arrays.toString(this.bootstrapMethodArguments);
   }
}
