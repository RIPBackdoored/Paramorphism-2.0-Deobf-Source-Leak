package org.eclipse.aether.transport.file;

final class PathUtils {
   private PathUtils() {
      super();
   }

   public static String protocol(String var0) {
      int var1 = var0.indexOf(":");
      return var1 == -1 ? "" : var0.substring(0, var1).trim();
   }

   public static String basedir(String var0) {
      String var1 = protocol(var0);
      String var2 = null;
      if (var1.length() > 0) {
         var2 = var0.substring(var1.length() + 1);
      } else {
         var2 = var0;
      }

      var2 = decode(var2);
      if (var2.startsWith("//")) {
         var2 = var2.substring(2);
         if (var2.length() >= 2 && (var2.charAt(1) == '|' || var2.charAt(1) == ':')) {
            var2 = var2.charAt(0) + ":" + var2.substring(2);
         } else {
            int var3 = var2.indexOf("/");
            if (var3 >= 0) {
               var2 = var2.substring(var3 + 1);
            }

            if (var2.length() >= 2 && (var2.charAt(1) == '|' || var2.charAt(1) == ':')) {
               var2 = var2.charAt(0) + ":" + var2.substring(2);
            } else if (var3 >= 0) {
               var2 = "/" + var2;
            }
         }
      }

      if (var2.length() >= 2 && var2.charAt(1) == '|') {
         var2 = var2.charAt(0) + ":" + var2.substring(2);
      }

      return var2.trim();
   }

   static String decode(String var0) {
      String var1 = var0;
      if (var0 != null) {
         int var2 = -1;

         while((var2 = var1.indexOf(37, var2 + 1)) >= 0) {
            if (var2 + 2 < var1.length()) {
               String var3 = var1.substring(var2 + 1, var2 + 3);
               char var4 = (char)Integer.parseInt(var3, 16);
               var1 = var1.substring(0, var2) + var4 + var1.substring(var2 + 3);
            }
         }
      }

      return var1;
   }
}
