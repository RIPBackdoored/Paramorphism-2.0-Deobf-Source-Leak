package com.fasterxml.jackson.core.io;

import java.util.Arrays;

public final class CharTypes {
   private static final char[] HC = "0123456789ABCDEF".toCharArray();
   private static final byte[] HB;
   private static final int[] sInputCodes;
   private static final int[] sInputCodesUTF8;
   private static final int[] sInputCodesJsNames;
   private static final int[] sInputCodesUtf8JsNames;
   private static final int[] sInputCodesComment;
   private static final int[] sInputCodesWS;
   private static final int[] sOutputEscapes128;
   private static final int[] sHexValues;

   public CharTypes() {
      super();
   }

   public static int[] getInputCodeLatin1() {
      return sInputCodes;
   }

   public static int[] getInputCodeUtf8() {
      return sInputCodesUTF8;
   }

   public static int[] getInputCodeLatin1JsNames() {
      return sInputCodesJsNames;
   }

   public static int[] getInputCodeUtf8JsNames() {
      return sInputCodesUtf8JsNames;
   }

   public static int[] getInputCodeComment() {
      return sInputCodesComment;
   }

   public static int[] getInputCodeWS() {
      return sInputCodesWS;
   }

   public static int[] get7BitOutputEscapes() {
      return sOutputEscapes128;
   }

   public static int charToHex(int var0) {
      return var0 > 127 ? -1 : sHexValues[var0];
   }

   public static void appendQuoted(StringBuilder var0, String var1) {
      int[] var2 = sOutputEscapes128;
      int var3 = var2.length;
      int var4 = 0;

      for(int var5 = var1.length(); var4 < var5; ++var4) {
         char var6 = var1.charAt(var4);
         if (var6 < var3 && var2[var6] != 0) {
            var0.append('\\');
            int var7 = var2[var6];
            if (var7 < 0) {
               var0.append('u');
               var0.append('0');
               var0.append('0');
               var0.append(HC[var6 >> 4]);
               var0.append(HC[var6 & 15]);
            } else {
               var0.append((char)var7);
            }
         } else {
            var0.append(var6);
         }
      }

   }

   public static char[] copyHexChars() {
      return (char[])((char[])HC.clone());
   }

   public static byte[] copyHexBytes() {
      return (byte[])((byte[])HB.clone());
   }

   static {
      int var0 = HC.length;
      HB = new byte[var0];

      int var1;
      for(var1 = 0; var1 < var0; ++var1) {
         HB[var1] = (byte)HC[var1];
      }

      int[] var3 = new int[256];

      for(var1 = 0; var1 < 32; ++var1) {
         var3[var1] = -1;
      }

      var3[34] = 1;
      var3[92] = 1;
      sInputCodes = var3;
      var3 = new int[sInputCodes.length];
      System.arraycopy(sInputCodes, 0, var3, 0, var3.length);

      for(var1 = 128; var1 < 256; ++var1) {
         byte var2;
         if ((var1 & 224) == 192) {
            var2 = 2;
         } else if ((var1 & 240) == 224) {
            var2 = 3;
         } else if ((var1 & 248) == 240) {
            var2 = 4;
         } else {
            var2 = -1;
         }

         var3[var1] = var2;
      }

      sInputCodesUTF8 = var3;
      var3 = new int[256];
      Arrays.fill(var3, -1);

      for(var1 = 33; var1 < 256; ++var1) {
         if (Character.isJavaIdentifierPart((char)var1)) {
            var3[var1] = 0;
         }
      }

      var3[64] = 0;
      var3[35] = 0;
      var3[42] = 0;
      var3[45] = 0;
      var3[43] = 0;
      sInputCodesJsNames = var3;
      var3 = new int[256];
      System.arraycopy(sInputCodesJsNames, 0, var3, 0, var3.length);
      Arrays.fill(var3, 128, 128, 0);
      sInputCodesUtf8JsNames = var3;
      var3 = new int[256];
      System.arraycopy(sInputCodesUTF8, 128, var3, 128, 128);
      Arrays.fill(var3, 0, 32, -1);
      var3[9] = 0;
      var3[10] = 10;
      var3[13] = 13;
      var3[42] = 42;
      sInputCodesComment = var3;
      var3 = new int[256];
      System.arraycopy(sInputCodesUTF8, 128, var3, 128, 128);
      Arrays.fill(var3, 0, 32, -1);
      var3[32] = 1;
      var3[9] = 1;
      var3[10] = 10;
      var3[13] = 13;
      var3[47] = 47;
      var3[35] = 35;
      sInputCodesWS = var3;
      var3 = new int[128];

      for(var1 = 0; var1 < 32; ++var1) {
         var3[var1] = -1;
      }

      var3[34] = 34;
      var3[92] = 92;
      var3[8] = 98;
      var3[9] = 116;
      var3[12] = 102;
      var3[10] = 110;
      var3[13] = 114;
      sOutputEscapes128 = var3;
      sHexValues = new int[128];
      Arrays.fill(sHexValues, -1);

      for(var0 = 0; var0 < 10; sHexValues[48 + var0] = var0++) {
      }

      for(var0 = 0; var0 < 6; ++var0) {
         sHexValues[97 + var0] = 10 + var0;
         sHexValues[65 + var0] = 10 + var0;
      }

   }
}
