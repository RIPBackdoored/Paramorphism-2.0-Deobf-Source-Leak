package com.fasterxml.jackson.databind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/** @deprecated */
@Deprecated
public class ISO8601Utils {
   protected static final int DEF_8601_LEN = "yyyy-MM-ddThh:mm:ss.SSS+00:00".length();
   private static final TimeZone TIMEZONE_Z = TimeZone.getTimeZone("UTC");

   public ISO8601Utils() {
      super();
   }

   public static String format(Date var0) {
      return format(var0, false, TIMEZONE_Z);
   }

   public static String format(Date var0, boolean var1) {
      return format(var0, var1, TIMEZONE_Z);
   }

   /** @deprecated */
   @Deprecated
   public static String format(Date var0, boolean var1, TimeZone var2) {
      return format(var0, var1, var2, Locale.US);
   }

   public static String format(Date var0, boolean var1, TimeZone var2, Locale var3) {
      GregorianCalendar var4 = new GregorianCalendar(var2, var3);
      var4.setTime(var0);
      StringBuilder var5 = new StringBuilder(30);
      var5.append(String.format("%04d-%02d-%02dT%02d:%02d:%02d", var4.get(1), var4.get(2) + 1, var4.get(5), var4.get(11), var4.get(12), var4.get(13)));
      if (var1) {
         var5.append(String.format(".%03d", var4.get(14)));
      }

      int var6 = var2.getOffset(var4.getTimeInMillis());
      if (var6 != 0) {
         int var7 = Math.abs(var6 / '\uea60' / 60);
         int var8 = Math.abs(var6 / '\uea60' % 60);
         var5.append(String.format("%c%02d:%02d", Character.valueOf((char)(var6 < 0 ? '-' : '+')), var7, var8));
      } else {
         var5.append('Z');
      }

      return var5.toString();
   }

   public static Date parse(String var0, ParsePosition var1) throws ParseException {
      Object var2 = null;

      Date var10000;
      try {
         int var19 = var1.getIndex();
         int var10001 = var19;
         var19 += 4;
         int var20 = parseInt(var0, var10001, var19);
         if (checkOffset(var0, var19, '-')) {
            ++var19;
         }

         var10001 = var19;
         var19 += 2;
         int var21 = parseInt(var0, var10001, var19);
         if (checkOffset(var0, var19, '-')) {
            ++var19;
         }

         var10001 = var19;
         var19 += 2;
         int var6 = parseInt(var0, var10001, var19);
         int var7 = 0;
         int var8 = 0;
         int var9 = 0;
         int var10 = 0;
         boolean var11 = checkOffset(var0, var19, 'T');
         if (!var11 && var0.length() <= var19) {
            GregorianCalendar var24 = new GregorianCalendar(var20, var21 - 1, var6);
            var1.setIndex(var19);
            var10000 = var24.getTime();
            return var10000;
         }

         if (var11) {
            ++var19;
            var10001 = var19;
            var19 += 2;
            var7 = parseInt(var0, var10001, var19);
            if (checkOffset(var0, var19, ':')) {
               ++var19;
            }

            var10001 = var19;
            var19 += 2;
            var8 = parseInt(var0, var10001, var19);
            if (checkOffset(var0, var19, ':')) {
               ++var19;
            }

            if (var0.length() > var19) {
               char var12 = var0.charAt(var19);
               if (var12 != 'Z' && var12 != '+' && var12 != '-') {
                  var10001 = var19;
                  var19 += 2;
                  var9 = parseInt(var0, var10001, var19);
                  if (var9 > 59 && var9 < 63) {
                     var9 = 59;
                  }

                  if (checkOffset(var0, var19, '.')) {
                     ++var19;
                     int var13 = indexOfNonDigit(var0, var19 + 1);
                     int var14 = Math.min(var13, var19 + 3);
                     int var15 = parseInt(var0, var19, var14);
                     switch(var14 - var19) {
                     case 1:
                        var10 = var15 * 100;
                        break;
                     case 2:
                        var10 = var15 * 10;
                        break;
                     default:
                        var10 = var15;
                     }

                     var19 = var13;
                  }
               }
            }
         }

         if (var0.length() <= var19) {
            throw new IllegalArgumentException("No time zone indicator");
         }

         TimeZone var22 = null;
         char var23 = var0.charAt(var19);
         if (var23 == 'Z') {
            var22 = TIMEZONE_Z;
            ++var19;
         } else {
            if (var23 != '+' && var23 != '-') {
               throw new IndexOutOfBoundsException("Invalid time zone indicator '" + var23 + "'");
            }

            String var25 = var0.substring(var19);
            var19 += var25.length();
            if (!"+0000".equals(var25) && !"+00:00".equals(var25)) {
               String var27 = "GMT" + var25;
               var22 = TimeZone.getTimeZone(var27);
               String var16 = var22.getID();
               if (!var16.equals(var27)) {
                  String var17 = var16.replace(":", "");
                  if (!var17.equals(var27)) {
                     throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + var27 + " given, resolves to " + var22.getID());
                  }
               }
            } else {
               var22 = TIMEZONE_Z;
            }
         }

         GregorianCalendar var26 = new GregorianCalendar(var22);
         var26.setLenient(false);
         var26.set(1, var20);
         var26.set(2, var21 - 1);
         var26.set(5, var6);
         var26.set(11, var7);
         var26.set(12, var8);
         var26.set(13, var9);
         var26.set(14, var10);
         var1.setIndex(var19);
         var10000 = var26.getTime();
      } catch (Exception var18) {
         String var3 = var0 == null ? null : '"' + var0 + '"';
         String var4 = var18.getMessage();
         if (var4 == null || var4.isEmpty()) {
            var4 = "(" + var18.getClass().getName() + ")";
         }

         ParseException var5 = new ParseException("Failed to parse date " + var3 + ": " + var4, var1.getIndex());
         var5.initCause(var18);
         throw var5;
      }

      return var10000;
   }

   private static boolean checkOffset(String var0, int var1, char var2) {
      return var1 < var0.length() && var0.charAt(var1) == var2;
   }

   private static int parseInt(String var0, int var1, int var2) throws NumberFormatException {
      if (var1 >= 0 && var2 <= var0.length() && var1 <= var2) {
         int var3 = var1;
         int var4 = 0;
         int var5;
         if (var1 < var2) {
            var3 = var1 + 1;
            var5 = Character.digit(var0.charAt(var1), 10);
            if (var5 < 0) {
               throw new NumberFormatException("Invalid number: " + var0.substring(var1, var2));
            }

            var4 = -var5;
         }

         while(var3 < var2) {
            var5 = Character.digit(var0.charAt(var3++), 10);
            if (var5 < 0) {
               throw new NumberFormatException("Invalid number: " + var0.substring(var1, var2));
            }

            var4 *= 10;
            var4 -= var5;
         }

         return -var4;
      } else {
         throw new NumberFormatException(var0);
      }
   }

   private static int indexOfNonDigit(String var0, int var1) {
      for(int var2 = var1; var2 < var0.length(); ++var2) {
         char var3 = var0.charAt(var2);
         if (var3 < '0' || var3 > '9') {
            return var2;
         }
      }

      return var0.length();
   }
}
