package org.jline.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

public final class Signals {
   private Signals() {
      super();
   }

   public static Object register(String var0, Runnable var1) {
      Objects.requireNonNull(var1);
      return register(var0, var1, var1.getClass().getClassLoader());
   }

   public static Object register(String var0, Runnable var1, ClassLoader var2) {
      Object var10000;
      try {
         Class var3 = Class.forName("sun.misc.SignalHandler");
         Object var4 = Proxy.newProxyInstance(var2, new Class[]{var3}, Signals::lambda$register$1);
         var10000 = doRegister(var0, var4);
      } catch (Exception var5) {
         Log.debug("Error registering handler for signal ", var0, var5);
         return null;
      }

      return var10000;
   }

   public static Object registerDefault(String var0) {
      Object var10000;
      try {
         Class var1 = Class.forName("sun.misc.SignalHandler");
         var10000 = doRegister(var0, var1.getField("SIG_DFL").get((Object)null));
      } catch (Exception var2) {
         Log.debug("Error registering default handler for signal ", var0, var2);
         return null;
      }

      return var10000;
   }

   public static void unregister(String var0, Object var1) {
      try {
         if (var1 != null) {
            doRegister(var0, var1);
         }
      } catch (Exception var3) {
         Log.debug("Error unregistering handler for signal ", var0, var3);
      }

   }

   private static Object doRegister(String var0, Object var1) throws Exception {
      Log.trace(Signals::lambda$doRegister$2);
      Class var2 = Class.forName("sun.misc.Signal");
      Constructor var3 = var2.getConstructor(String.class);

      Object var4;
      try {
         var4 = var3.newInstance(var0);
      } catch (IllegalArgumentException var6) {
         Log.trace(Signals::lambda$doRegister$3);
         return null;
      }

      Class var5 = Class.forName("sun.misc.SignalHandler");
      return var2.getMethod("handle", var2, var5).invoke((Object)null, var4, var1);
   }

   private static String toString(Object var0) {
      String var10000;
      try {
         Class var1 = Class.forName("sun.misc.SignalHandler");
         if (var0 == var1.getField("SIG_DFL").get((Object)null)) {
            var10000 = "SIG_DFL";
            return var10000;
         }

         if (var0 != var1.getField("SIG_IGN").get((Object)null)) {
            return var0 != null ? var0.toString() : "null";
         }

         var10000 = "SIG_IGN";
      } catch (Throwable var2) {
         return var0 != null ? var0.toString() : "null";
      }

      return var10000;
   }

   private static String lambda$doRegister$3(String var0) {
      return "Ignoring unsupported signal " + var0;
   }

   private static String lambda$doRegister$2(String var0, Object var1) {
      return "Registering signal " + var0 + " with handler " + toString(var1);
   }

   private static Object lambda$register$1(Runnable var0, Class var1, String var2, Object var3, Method var4, Object[] var5) throws Throwable {
      if (var4.getDeclaringClass() == Object.class) {
         if ("toString".equals(var4.getName())) {
            return var0.toString();
         }
      } else if (var4.getDeclaringClass() == var1) {
         Log.trace(Signals::lambda$null$0);
         var0.run();
      }

      return null;
   }

   private static String lambda$null$0(Runnable var0, String var1) {
      return "Calling handler " + toString(var0) + " for signal " + var1;
   }
}
