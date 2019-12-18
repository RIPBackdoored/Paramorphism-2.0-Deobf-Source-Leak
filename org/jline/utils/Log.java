package org.jline.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class Log {
   public Log() {
      super();
   }

   public static void trace(Object... var0) {
      log(Level.FINEST, var0);
   }

   public static void trace(Supplier var0) {
      log(Level.FINEST, var0);
   }

   public static void debug(Supplier var0) {
      log(Level.FINE, var0);
   }

   public static void debug(Object... var0) {
      log(Level.FINE, var0);
   }

   public static void info(Object... var0) {
      log(Level.INFO, var0);
   }

   public static void warn(Object... var0) {
      log(Level.WARNING, var0);
   }

   public static void error(Object... var0) {
      log(Level.SEVERE, var0);
   }

   public static boolean isDebugEnabled() {
      return isEnabled(Level.FINE);
   }

   static void render(PrintStream var0, Object var1) {
      if (var1 != null && var1.getClass().isArray()) {
         Object[] var2 = (Object[])((Object[])var1);
         var0.print("[");

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var0.print(var2[var3]);
            if (var3 + 1 < var2.length) {
               var0.print(",");
            }
         }

         var0.print("]");
      } else {
         var0.print(var1);
      }

   }

   static LogRecord createRecord(Level var0, Object... var1) {
      Throwable var2 = null;
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      PrintStream var4 = new PrintStream(var3);

      for(int var5 = 0; var5 < var1.length; ++var5) {
         if (var5 + 1 == var1.length && var1[var5] instanceof Throwable) {
            var2 = (Throwable)var1[var5];
         } else {
            render(var4, var1[var5]);
         }
      }

      var4.close();
      LogRecord var6 = new LogRecord(var0, var3.toString());
      var6.setThrown(var2);
      return var6;
   }

   static LogRecord createRecord(Level var0, Supplier var1) {
      return new LogRecord(var0, (String)var1.get());
   }

   static void log(Level var0, Supplier var1) {
      logr(var0, Log::lambda$log$0);
   }

   static void log(Level var0, Object... var1) {
      logr(var0, Log::lambda$log$1);
   }

   static void logr(Level var0, Supplier var1) {
      Logger var2 = Logger.getLogger("org.jline");
      if (var2.isLoggable(var0)) {
         LogRecord var3 = (LogRecord)var1.get();
         var3.setLoggerName(var2.getName());
         var2.log(var3);
      }

   }

   static boolean isEnabled(Level var0) {
      Logger var1 = Logger.getLogger("org.jline");
      return var1.isLoggable(var0);
   }

   private static LogRecord lambda$log$1(Level var0, Object[] var1) {
      return createRecord(var0, var1);
   }

   private static LogRecord lambda$log$0(Level var0, Supplier var1) {
      return createRecord(var0, var1);
   }
}
