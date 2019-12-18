package com.fasterxml.jackson.databind.util;

public abstract class NameTransformer {
   public static final NameTransformer NOP = new NameTransformer$NopTransformer();

   protected NameTransformer() {
      super();
   }

   public static NameTransformer simpleTransformer(String var0, String var1) {
      boolean var2 = var0 != null && var0.length() > 0;
      boolean var3 = var1 != null && var1.length() > 0;
      if (var2) {
         return (NameTransformer)(var3 ? new NameTransformer$1(var0, var1) : new NameTransformer$2(var0));
      } else {
         return (NameTransformer)(var3 ? new NameTransformer$3(var1) : NOP);
      }
   }

   public static NameTransformer chainedTransformer(NameTransformer var0, NameTransformer var1) {
      return new NameTransformer$Chained(var0, var1);
   }

   public abstract String transform(String var1);

   public abstract String reverse(String var1);
}
