package org.eclipse.aether.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ChecksumUtils {
   private ChecksumUtils() {
      super();
   }

   public static String read(File var0) throws IOException {
      String var1 = "";
      BufferedReader var2 = new BufferedReader(new InputStreamReader(new FileInputStream(var0), StandardCharsets.UTF_8), 512);
      Throwable var3 = null;

      while(true) {
         boolean var11 = false;

         try {
            var11 = true;
            String var4 = var2.readLine();
            if (var4 == null) {
               var11 = false;
               break;
            }

            var4 = var4.trim();
            if (var4.length() > 0) {
               var1 = var4;
               var11 = false;
               break;
            }
         } catch (Throwable var14) {
            var3 = var14;
            throw var14;
         } finally {
            if (var11) {
               if (var2 != null) {
                  if (var3 != null) {
                     try {
                        var2.close();
                     } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                     }
                  } else {
                     var2.close();
                  }
               }

            }
         }
      }

      if (var2 != null) {
         if (var3 != null) {
            try {
               var2.close();
            } catch (Throwable var13) {
               var3.addSuppressed(var13);
            }
         } else {
            var2.close();
         }
      }

      int var16;
      if (var1.matches(".+= [0-9A-Fa-f]+")) {
         var16 = var1.lastIndexOf(32);
         var1 = var1.substring(var16 + 1);
      } else {
         var16 = var1.indexOf(32);
         if (var16 != -1) {
            var1 = var1.substring(0, var16);
         }
      }

      return var1;
   }

   public static Map calc(File var0, Collection var1) throws IOException {
      return calc((InputStream)(new FileInputStream(var0)), var1);
   }

   public static Map calc(byte[] var0, Collection var1) throws IOException {
      return calc((InputStream)(new ByteArrayInputStream(var0)), var1);
   }

   private static Map calc(InputStream var0, Collection var1) throws IOException {
      LinkedHashMap var2 = new LinkedHashMap();
      LinkedHashMap var3 = new LinkedHashMap();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();

         try {
            var3.put(var5, MessageDigest.getInstance(var5));
         } catch (NoSuchAlgorithmException var20) {
            var2.put(var5, var20);
         }
      }

      InputStream var23 = var0;
      Throwable var24 = null;
      boolean var17 = false;

      byte[] var6;
      try {
         var17 = true;
         var6 = new byte['耀'];

         while(true) {
            int var7 = var23.read(var6);
            if (var7 < 0) {
               var17 = false;
               break;
            }

            Iterator var8 = var3.values().iterator();

            while(var8.hasNext()) {
               MessageDigest var9 = (MessageDigest)var8.next();
               var9.update(var6, 0, var7);
            }
         }
      } catch (Throwable var21) {
         var24 = var21;
         throw var21;
      } finally {
         if (var17) {
            if (var23 != null) {
               if (var24 != null) {
                  try {
                     var23.close();
                  } catch (Throwable var18) {
                     var24.addSuppressed(var18);
                  }
               } else {
                  var23.close();
               }
            }

         }
      }

      if (var23 != null) {
         if (var24 != null) {
            try {
               var23.close();
            } catch (Throwable var19) {
               var24.addSuppressed(var19);
            }
         } else {
            var23.close();
         }
      }

      var4 = var3.entrySet().iterator();

      while(var4.hasNext()) {
         Entry var25 = (Entry)var4.next();
         var6 = ((MessageDigest)var25.getValue()).digest();
         var2.put(var25.getKey(), toHexString(var6));
      }

      return var2;
   }

   public static String toHexString(byte[] var0) {
      if (var0 == null) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder(var0.length * 2);
         byte[] var2 = var0;
         int var3 = var0.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            byte var5 = var2[var4];
            int var6 = var5 & 255;
            if (var6 < 16) {
               var1.append('0');
            }

            var1.append(Integer.toHexString(var6));
         }

         return var1.toString();
      }
   }
}
