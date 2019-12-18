package org.objectweb.asm.commons;

import java.util.Collections;
import java.util.Map;

public class SimpleRemapper extends Remapper {
   private final Map mapping;

   public SimpleRemapper(Map var1) {
      super();
      this.mapping = var1;
   }

   public SimpleRemapper(String var1, String var2) {
      super();
      this.mapping = Collections.singletonMap(var1, var2);
   }

   public String mapMethodName(String var1, String var2, String var3) {
      String var4 = this.map(var1 + '.' + var2 + var3);
      return var4 == null ? var2 : var4;
   }

   public String mapInvokeDynamicMethodName(String var1, String var2) {
      String var3 = this.map('.' + var1 + var2);
      return var3 == null ? var1 : var3;
   }

   public String mapFieldName(String var1, String var2, String var3) {
      String var4 = this.map(var1 + '.' + var2);
      return var4 == null ? var2 : var4;
   }

   public String map(String var1) {
      return (String)this.mapping.get(var1);
   }
}
