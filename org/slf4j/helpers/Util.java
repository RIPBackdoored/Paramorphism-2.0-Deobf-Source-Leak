package org.slf4j.helpers;

public final class Util {
   private static Util$ClassContextSecurityManager SECURITY_MANAGER;
   private static boolean SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = false;

   private Util() {
      super();
   }

   public static String safeGetSystemProperty(String var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("null input");
      } else {
         String var1 = null;

         try {
            var1 = System.getProperty(var0);
         } catch (SecurityException var3) {
         }

         return var1;
      }
   }

   public static boolean safeGetBooleanSystemProperty(String var0) {
      String var1 = safeGetSystemProperty(var0);
      return var1 == null ? false : var1.equalsIgnoreCase("true");
   }

   private static Util$ClassContextSecurityManager getSecurityManager() {
      if (SECURITY_MANAGER != null) {
         return SECURITY_MANAGER;
      } else if (SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED) {
         return null;
      } else {
         SECURITY_MANAGER = safeCreateSecurityManager();
         SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = true;
         return SECURITY_MANAGER;
      }
   }

   private static Util$ClassContextSecurityManager safeCreateSecurityManager() {
      Util$ClassContextSecurityManager var10000;
      try {
         var10000 = new Util$ClassContextSecurityManager((Util$1)null);
      } catch (SecurityException var1) {
         return null;
      }

      return var10000;
   }

   public static Class getCallingClass() {
      Util$ClassContextSecurityManager var0 = getSecurityManager();
      if (var0 == null) {
         return null;
      } else {
         Class[] var1 = var0.getClassContext();
         String var2 = Util.class.getName();

         int var3;
         for(var3 = 0; var3 < var1.length && !var2.equals(var1[var3].getName()); ++var3) {
         }

         if (var3 < var1.length && var3 + 2 < var1.length) {
            return var1[var3 + 2];
         } else {
            throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen");
         }
      }
   }

   public static final void report(String var0, Throwable var1) {
      System.err.println(var0);
      System.err.println("Reported exception:");
      var1.printStackTrace();
   }

   public static final void report(String var0) {
      System.err.println("SLF4J: " + var0);
   }
}
