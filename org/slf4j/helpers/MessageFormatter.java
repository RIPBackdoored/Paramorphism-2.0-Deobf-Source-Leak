package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;

public final class MessageFormatter {
   static final char DELIM_START = '{';
   static final char DELIM_STOP = '}';
   static final String DELIM_STR = "{}";
   private static final char ESCAPE_CHAR = '\\';

   public MessageFormatter() {
      super();
   }

   public static final FormattingTuple format(String var0, Object var1) {
      return arrayFormat(var0, new Object[]{var1});
   }

   public static final FormattingTuple format(String var0, Object var1, Object var2) {
      return arrayFormat(var0, new Object[]{var1, var2});
   }

   static final Throwable getThrowableCandidate(Object[] var0) {
      if (var0 != null && var0.length != 0) {
         Object var1 = var0[var0.length - 1];
         return var1 instanceof Throwable ? (Throwable)var1 : null;
      } else {
         return null;
      }
   }

   public static final FormattingTuple arrayFormat(String var0, Object[] var1) {
      Throwable var2 = getThrowableCandidate(var1);
      Object[] var3 = var1;
      if (var2 != null) {
         var3 = trimmedCopy(var1);
      }

      return arrayFormat(var0, var3, var2);
   }

   private static Object[] trimmedCopy(Object[] var0) {
      if (var0 != null && var0.length != 0) {
         int var1 = var0.length - 1;
         Object[] var2 = new Object[var1];
         System.arraycopy(var0, 0, var2, 0, var1);
         return var2;
      } else {
         throw new IllegalStateException("non-sensical empty or null argument array");
      }
   }

   public static final FormattingTuple arrayFormat(String var0, Object[] var1, Throwable var2) {
      if (var0 == null) {
         return new FormattingTuple((String)null, var1, var2);
      } else if (var1 == null) {
         return new FormattingTuple(var0);
      } else {
         int var3 = 0;
         StringBuilder var5 = new StringBuilder(var0.length() + 50);

         for(int var6 = 0; var6 < var1.length; ++var6) {
            int var4 = var0.indexOf("{}", var3);
            if (var4 == -1) {
               if (var3 == 0) {
                  return new FormattingTuple(var0, var1, var2);
               }

               var5.append(var0, var3, var0.length());
               return new FormattingTuple(var5.toString(), var1, var2);
            }

            if (isEscapedDelimeter(var0, var4)) {
               if (!isDoubleEscaped(var0, var4)) {
                  --var6;
                  var5.append(var0, var3, var4 - 1);
                  var5.append('{');
                  var3 = var4 + 1;
               } else {
                  var5.append(var0, var3, var4 - 1);
                  deeplyAppendParameter(var5, var1[var6], new HashMap());
                  var3 = var4 + 2;
               }
            } else {
               var5.append(var0, var3, var4);
               deeplyAppendParameter(var5, var1[var6], new HashMap());
               var3 = var4 + 2;
            }
         }

         var5.append(var0, var3, var0.length());
         return new FormattingTuple(var5.toString(), var1, var2);
      }
   }

   static final boolean isEscapedDelimeter(String var0, int var1) {
      if (var1 == 0) {
         return false;
      } else {
         char var2 = var0.charAt(var1 - 1);
         return var2 == '\\';
      }
   }

   static final boolean isDoubleEscaped(String var0, int var1) {
      return var1 >= 2 && var0.charAt(var1 - 2) == '\\';
   }

   private static void deeplyAppendParameter(StringBuilder var0, Object var1, Map var2) {
      if (var1 == null) {
         var0.append("null");
      } else {
         if (!var1.getClass().isArray()) {
            safeObjectAppend(var0, var1);
         } else if (var1 instanceof boolean[]) {
            booleanArrayAppend(var0, (boolean[])((boolean[])var1));
         } else if (var1 instanceof byte[]) {
            byteArrayAppend(var0, (byte[])((byte[])var1));
         } else if (var1 instanceof char[]) {
            charArrayAppend(var0, (char[])((char[])var1));
         } else if (var1 instanceof short[]) {
            shortArrayAppend(var0, (short[])((short[])var1));
         } else if (var1 instanceof int[]) {
            intArrayAppend(var0, (int[])((int[])var1));
         } else if (var1 instanceof long[]) {
            longArrayAppend(var0, (long[])((long[])var1));
         } else if (var1 instanceof float[]) {
            floatArrayAppend(var0, (float[])((float[])var1));
         } else if (var1 instanceof double[]) {
            doubleArrayAppend(var0, (double[])((double[])var1));
         } else {
            objectArrayAppend(var0, (Object[])((Object[])var1), var2);
         }

      }
   }

   private static void safeObjectAppend(StringBuilder var0, Object var1) {
      try {
         String var2 = var1.toString();
         var0.append(var2);
      } catch (Throwable var3) {
         Util.report("SLF4J: Failed toString() invocation on an object of type [" + var1.getClass().getName() + "]", var3);
         var0.append("[FAILED toString()]");
      }

   }

   private static void objectArrayAppend(StringBuilder var0, Object[] var1, Map var2) {
      var0.append('[');
      if (!var2.containsKey(var1)) {
         var2.put(var1, (Object)null);
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            deeplyAppendParameter(var0, var1[var4], var2);
            if (var4 != var3 - 1) {
               var0.append(", ");
            }
         }

         var2.remove(var1);
      } else {
         var0.append("...");
      }

      var0.append(']');
   }

   private static void booleanArrayAppend(StringBuilder var0, boolean[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }

   private static void byteArrayAppend(StringBuilder var0, byte[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }

   private static void charArrayAppend(StringBuilder var0, char[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }

   private static void shortArrayAppend(StringBuilder var0, short[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }

   private static void intArrayAppend(StringBuilder var0, int[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }

   private static void longArrayAppend(StringBuilder var0, long[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }

   private static void floatArrayAppend(StringBuilder var0, float[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }

   private static void doubleArrayAppend(StringBuilder var0, double[] var1) {
      var0.append('[');
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var0.append(var1[var3]);
         if (var3 != var2 - 1) {
            var0.append(", ");
         }
      }

      var0.append(']');
   }
}
