package com.fasterxml.jackson.core.io;

import java.math.BigDecimal;

public final class NumberInput {
   public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";
   static final long L_BILLION = 1000000000L;
   static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
   static final String MAX_LONG_STR = String.valueOf(4294967295L);

   public NumberInput() {
      super();
   }

   public static int parseInt(char[] var0, int var1, int var2) {
      int var3 = var0[var1] - 48;
      int var10000;
      if (var2 > 4) {
         var10000 = var3 * 10;
         ++var1;
         var3 = var10000 + (var0[var1] - 48);
         var10000 = var3 * 10;
         ++var1;
         var3 = var10000 + (var0[var1] - 48);
         var10000 = var3 * 10;
         ++var1;
         var3 = var10000 + (var0[var1] - 48);
         var10000 = var3 * 10;
         ++var1;
         var3 = var10000 + (var0[var1] - 48);
         var2 -= 4;
         if (var2 > 4) {
            var10000 = var3 * 10;
            ++var1;
            var3 = var10000 + (var0[var1] - 48);
            var10000 = var3 * 10;
            ++var1;
            var3 = var10000 + (var0[var1] - 48);
            var10000 = var3 * 10;
            ++var1;
            var3 = var10000 + (var0[var1] - 48);
            var10000 = var3 * 10;
            ++var1;
            var3 = var10000 + (var0[var1] - 48);
            return var3;
         }
      }

      if (var2 > 1) {
         var10000 = var3 * 10;
         ++var1;
         var3 = var10000 + (var0[var1] - 48);
         if (var2 > 2) {
            var10000 = var3 * 10;
            ++var1;
            var3 = var10000 + (var0[var1] - 48);
            if (var2 > 3) {
               var10000 = var3 * 10;
               ++var1;
               var3 = var10000 + (var0[var1] - 48);
            }
         }
      }

      return var3;
   }

   public static int parseInt(String var0) {
      char var1 = var0.charAt(0);
      int var2 = var0.length();
      boolean var3 = var1 == '-';
      int var4 = 1;
      if (var3) {
         if (var2 == 1 || var2 > 10) {
            return Integer.parseInt(var0);
         }

         var1 = var0.charAt(var4++);
      } else if (var2 > 9) {
         return Integer.parseInt(var0);
      }

      if (var1 <= '9' && var1 >= '0') {
         int var5 = var1 - 48;
         if (var4 < var2) {
            var1 = var0.charAt(var4++);
            if (var1 > '9' || var1 < '0') {
               return Integer.parseInt(var0);
            }

            var5 = var5 * 10 + (var1 - 48);
            if (var4 < var2) {
               var1 = var0.charAt(var4++);
               if (var1 <= '9' && var1 >= '0') {
                  var5 = var5 * 10 + (var1 - 48);
                  if (var4 >= var2) {
                     return var3 ? -var5 : var5;
                  }

                  do {
                     var1 = var0.charAt(var4++);
                     if (var1 > '9' || var1 < '0') {
                        return Integer.parseInt(var0);
                     }

                     var5 = var5 * 10 + (var1 - 48);
                  } while(var4 < var2);

                  return var3 ? -var5 : var5;
               }

               return Integer.parseInt(var0);
            }
         }

         return var3 ? -var5 : var5;
      } else {
         return Integer.parseInt(var0);
      }
   }

   public static long parseLong(char[] var0, int var1, int var2) {
      int var3 = var2 - 9;
      long var4 = (long)parseInt(var0, var1, var3) * 1000000000L;
      return var4 + (long)parseInt(var0, var1 + var3, 9);
   }

   public static long parseLong(String var0) {
      int var1 = var0.length();
      return var1 <= 9 ? (long)parseInt(var0) : Long.parseLong(var0);
   }

   public static boolean inLongRange(char[] var0, int var1, int var2, boolean var3) {
      String var4 = var3 ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
      int var5 = var4.length();
      if (var2 < var5) {
         return true;
      } else if (var2 > var5) {
         return false;
      } else {
         for(int var6 = 0; var6 < var5; ++var6) {
            int var7 = var0[var1 + var6] - var4.charAt(var6);
            if (var7 != 0) {
               return var7 < 0;
            }
         }

         return true;
      }
   }

   public static boolean inLongRange(String var0, boolean var1) {
      String var2 = var1 ? MIN_LONG_STR_NO_SIGN : MAX_LONG_STR;
      int var3 = var2.length();
      int var4 = var0.length();
      if (var4 < var3) {
         return true;
      } else if (var4 > var3) {
         return false;
      } else {
         for(int var5 = 0; var5 < var3; ++var5) {
            int var6 = var0.charAt(var5) - var2.charAt(var5);
            if (var6 != 0) {
               return var6 < 0;
            }
         }

         return true;
      }
   }

   public static int parseAsInt(String var0, int var1) {
      if (var0 == null) {
         return var1;
      } else {
         var0 = var0.trim();
         int var2 = var0.length();
         if (var2 == 0) {
            return var1;
         } else {
            int var3 = 0;
            char var4;
            if (var3 < var2) {
               var4 = var0.charAt(0);
               if (var4 == '+') {
                  var0 = var0.substring(1);
                  var2 = var0.length();
               } else if (var4 == '-') {
                  ++var3;
               }
            }

            int var10000;
            while(var3 < var2) {
               var4 = var0.charAt(var3);
               if (var4 > '9' || var4 < '0') {
                  try {
                     var10000 = (int)parseDouble(var0);
                  } catch (NumberFormatException var6) {
                     return var1;
                  }

                  return var10000;
               }

               ++var3;
            }

            try {
               var10000 = Integer.parseInt(var0);
            } catch (NumberFormatException var7) {
               return var1;
            }

            return var10000;
         }
      }
   }

   public static long parseAsLong(String var0, long var1) {
      if (var0 == null) {
         return var1;
      } else {
         var0 = var0.trim();
         int var3 = var0.length();
         if (var3 == 0) {
            return var1;
         } else {
            int var4 = 0;
            char var5;
            if (var4 < var3) {
               var5 = var0.charAt(0);
               if (var5 == '+') {
                  var0 = var0.substring(1);
                  var3 = var0.length();
               } else if (var5 == '-') {
                  ++var4;
               }
            }

            long var10000;
            while(var4 < var3) {
               var5 = var0.charAt(var4);
               if (var5 > '9' || var5 < '0') {
                  try {
                     var10000 = (long)parseDouble(var0);
                  } catch (NumberFormatException var7) {
                     return var1;
                  }

                  return var10000;
               }

               ++var4;
            }

            try {
               var10000 = Long.parseLong(var0);
            } catch (NumberFormatException var8) {
               return var1;
            }

            return var10000;
         }
      }
   }

   public static double parseAsDouble(String var0, double var1) {
      if (var0 == null) {
         return var1;
      } else {
         var0 = var0.trim();
         int var3 = var0.length();
         if (var3 == 0) {
            return var1;
         } else {
            double var10000;
            try {
               var10000 = parseDouble(var0);
            } catch (NumberFormatException var5) {
               return var1;
            }

            return var10000;
         }
      }
   }

   public static double parseDouble(String var0) throws NumberFormatException {
      return "2.2250738585072012e-308".equals(var0) ? Double.MIN_VALUE : Double.parseDouble(var0);
   }

   public static BigDecimal parseBigDecimal(String var0) throws NumberFormatException {
      BigDecimal var10000;
      try {
         var10000 = new BigDecimal(var0);
      } catch (NumberFormatException var2) {
         throw _badBD(var0);
      }

      return var10000;
   }

   public static BigDecimal parseBigDecimal(char[] var0) throws NumberFormatException {
      return parseBigDecimal(var0, 0, var0.length);
   }

   public static BigDecimal parseBigDecimal(char[] var0, int var1, int var2) throws NumberFormatException {
      BigDecimal var10000;
      try {
         var10000 = new BigDecimal(var0, var1, var2);
      } catch (NumberFormatException var4) {
         throw _badBD(new String(var0, var1, var2));
      }

      return var10000;
   }

   private static NumberFormatException _badBD(String var0) {
      return new NumberFormatException("Value \"" + var0 + "\" can not be represented as BigDecimal");
   }
}
