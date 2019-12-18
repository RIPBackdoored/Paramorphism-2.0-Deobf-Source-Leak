package org.jline.style;

import java.util.function.Function;

public final class InterpolationHelper {
   private static final char ESCAPE_CHAR = '\\';
   private static final String DELIM_START = "@{";
   private static final String DELIM_STOP = "}";
   private static final String MARKER = "@__";

   private InterpolationHelper() {
      super();
   }

   public static String substVars(String var0, Function var1, boolean var2) throws IllegalArgumentException {
      return unescape(doSubstVars(var0, var1, var2));
   }

   private static String doSubstVars(String var0, Function var1, boolean var2) throws IllegalArgumentException {
      int var4 = -1;

      int var3;
      do {
         for(var4 = var0.indexOf("}", var4 + 1); var4 > 0 && var0.charAt(var4 - 1) == '\\'; var4 = var0.indexOf("}", var4 + 1)) {
         }

         var3 = var0.indexOf("@{");

         while(var4 >= 0) {
            int var5 = var0.indexOf("@{", var3 + "@{".length());
            if (var5 < 0 || var5 > var4) {
               break;
            }

            if (var5 < var4) {
               var3 = var5;
            }
         }
      } while(var3 >= 0 && var4 >= 0 && var4 < var3 + "@{".length());

      if (var3 >= 0 && var4 >= 0) {
         String var7 = var0.substring(var3 + "@{".length(), var4);
         String var6 = null;
         if (var7.length() > 0 && var1 != null) {
            var6 = (String)var1.apply(var7);
         }

         if (var6 == null) {
            if (var2) {
               var6 = "";
            } else {
               var6 = "@__{" + var7 + "}";
            }
         }

         var0 = var0.substring(0, var3) + var6 + var0.substring(var4 + "}".length(), var0.length());
         var0 = doSubstVars(var0, var1, var2);
         return var0;
      } else {
         return var0;
      }
   }

   private static String unescape(String var0) {
      var0 = var0.replaceAll("@__", "@");

      for(int var1 = var0.indexOf(92); var1 >= 0 && var1 < var0.length() - 1; var1 = var0.indexOf(92, var1 + 1)) {
         char var2 = var0.charAt(var1 + 1);
         if (var2 == '{' || var2 == '}' || var2 == '\\') {
            var0 = var0.substring(0, var1) + var0.substring(var1 + 1);
         }
      }

      return var0;
   }
}
