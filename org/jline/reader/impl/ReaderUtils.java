package org.jline.reader.impl;

import org.jline.reader.LineReader;
import org.jline.reader.LineReader$Option;

public class ReaderUtils {
   private ReaderUtils() {
      super();
   }

   public static boolean isSet(LineReader var0, LineReader$Option var1) {
      return var0 != null && var0.isSet(var1);
   }

   public static String getString(LineReader var0, String var1, String var2) {
      Object var3 = var0 != null ? var0.getVariable(var1) : null;
      return var3 != null ? var3.toString() : var2;
   }

   public static boolean getBoolean(LineReader var0, String var1, boolean var2) {
      Object var3 = var0 != null ? var0.getVariable(var1) : null;
      if (var3 instanceof Boolean) {
         return (Boolean)var3;
      } else if (var3 == null) {
         return var2;
      } else {
         String var4 = var3.toString();
         return var4.isEmpty() || var4.equalsIgnoreCase("on") || var4.equalsIgnoreCase("1") || var4.equalsIgnoreCase("true");
      }
   }

   public static int getInt(LineReader var0, String var1, int var2) {
      int var3 = var2;
      Object var4 = var0 != null ? var0.getVariable(var1) : null;
      if (var4 instanceof Number) {
         return ((Number)var4).intValue();
      } else {
         if (var4 != null) {
            var3 = 0;

            try {
               var3 = Integer.parseInt(var4.toString());
            } catch (NumberFormatException var6) {
            }
         }

         return var3;
      }
   }

   public static long getLong(LineReader var0, String var1, long var2) {
      long var4 = var2;
      Object var6 = var0 != null ? var0.getVariable(var1) : null;
      if (var6 instanceof Number) {
         return ((Number)var6).longValue();
      } else {
         if (var6 != null) {
            var4 = 0L;

            try {
               var4 = Long.parseLong(var6.toString());
            } catch (NumberFormatException var8) {
            }
         }

         return var4;
      }
   }
}
