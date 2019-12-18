package dev.paramorphism.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import sun.misc.Unsafe;

public class do {
   private static boolean final = false;
   private static ClassLoader while;
   private static Method private;
   private static Map continue;
   private static Map do;
   private static final Unsafe short = class();

   private static Unsafe class() {
      Field var0 = Unsafe.class.getDeclaredField("theUnsafe");
      var0.setAccessible(true);
      return (Unsafe)var0.get((Object)null);
   }

   private static void import() {
      String var0 = System.getProperty("os.name").toLowerCase();
      if (var0.contains("windows")) {
         String var1 = System.getProperty("java.vm.name");
         String var2 = var1.contains("Client VM") ? "/bin/client/jvm.dll" : "/bin/server/jvm.dll";
         System.load(System.getProperty("java.home") + var2);
         while = do.class.getClassLoader();
      }

      private = ClassLoader.class.getDeclaredMethod("findNative", ClassLoader.class, String.class);

      try {
         Class var4 = ClassLoader.getSystemClassLoader().loadClass("jdk.internal.module.IllegalAccessLogger");
         Field var5 = var4.getDeclaredField("logger");
         short.putObjectVolatile(var4, short.staticFieldOffset(var5), (Object)null);
      } catch (Throwable var3) {
      }

      private.setAccessible(true);
   }

   private static void interface() {
      import();
      continue = new HashMap();
      do = new HashMap();
      volatile(do);
      false(continue, do);
   }

   private static long package(String var0) {
      long var1 = (Long)private.invoke((Object)null, while, var0);
      if (var1 == 0L) {
         throw new NoSuchElementException(var0);
      } else {
         return short.getLong(var1);
      }
   }

   private static Set abstract(Object[] var0) {
      return (Set)var0[6];
   }

   private static long else(Object[] var0) {
      return (Long)var0[2];
   }

   private static Object[] return(Object[] var0, String var1) {
      Iterator var2 = abstract(var0).iterator();

      Object[] var3;
      do {
         if (!var2.hasNext()) {
            throw new NoSuchElementException(var1);
         }

         var3 = (Object[])var2.next();
      } while(!var3[0].equals(var1));

      return var3;
   }

   private static String do(long var0) {
      if (var0 == 0L) {
         return null;
      } else {
         char[] var2 = new char[40];

         int var3;
         byte var4;
         for(var3 = 0; (var4 = short.getByte(var0 + (long)var3)) != 0; var2[var3++] = (char)var4) {
            if (var3 >= var2.length) {
               var2 = Arrays.copyOf(var2, var3 * 2);
            }
         }

         return new String(var2, 0, var3);
      }
   }

   private static void volatile(Map var0) {
      long var1 = package("gHotSpotVMStructs");
      long var3 = package("gHotSpotVMStructEntryTypeNameOffset");
      long var5 = package("gHotSpotVMStructEntryFieldNameOffset");
      long var7 = package("gHotSpotVMStructEntryTypeStringOffset");
      long var9 = package("gHotSpotVMStructEntryIsStaticOffset");
      long var11 = package("gHotSpotVMStructEntryOffsetOffset");
      long var13 = package("gHotSpotVMStructEntryAddressOffset");
      long var15 = package("gHotSpotVMStructEntryArrayStride");

      while(true) {
         String var17 = do(short.getLong(var1 + var3));
         String var18 = do(short.getLong(var1 + var5));
         if (var18 == null) {
            return;
         }

         String var19 = do(short.getLong(var1 + var7));
         boolean var20 = short.getInt(var1 + var9) != 0;
         long var21 = short.getLong(var1 + (var20 ? var13 : var11));
         Object var23 = (Set)var0.get(var17);
         if (var23 == null) {
            var0.put(var17, var23 = new HashSet());
         }

         ((Set)var23).add(new Object[]{var18, var19, var21, var20});
         var1 += var15;
      }
   }

   private static void false(Map var0, Map var1) {
      long var2 = package("gHotSpotVMTypes");
      long var4 = package("gHotSpotVMTypeEntryTypeNameOffset");
      long var6 = package("gHotSpotVMTypeEntrySuperclassNameOffset");
      long var8 = package("gHotSpotVMTypeEntryIsOopTypeOffset");
      long var10 = package("gHotSpotVMTypeEntryIsIntegerTypeOffset");
      long var12 = package("gHotSpotVMTypeEntryIsUnsignedOffset");
      long var14 = package("gHotSpotVMTypeEntrySizeOffset");
      long var16 = package("gHotSpotVMTypeEntryArrayStride");

      while(true) {
         String var18 = do(short.getLong(var2 + var4));
         if (var18 == null) {
            return;
         }

         String var19 = do(short.getLong(var2 + var6));
         boolean var20 = short.getInt(var2 + var8) != 0;
         boolean var21 = short.getInt(var2 + var10) != 0;
         boolean var22 = short.getInt(var2 + var12) != 0;
         int var23 = short.getInt(var2 + var14);
         Set var24 = (Set)var1.get(var18);
         var0.put(var18, new Object[]{var18, var19, var23, var20, var21, var22, var24});
         var2 += var16;
      }
   }

   private static Object[] public() {
      Object[] var0;
      if ((var0 = (Object[])continue.get("Flag")) != null) {
         return var0;
      } else {
         return (var0 = (Object[])continue.get("JVMFlag")) != null ? var0 : null;
      }
   }

   public static void run() {
      if (!final) {
         final = true;
         interface();
         Object[] var2 = public();
         if (var2 == null) {
            throw new RuntimeException();
         } else {
            int var3 = (Integer)var2[2];
            Object[] var4 = return(var2, "flags");
            long var5 = short.getAddress(else(var4));
            Object[] var7 = return(var2, "numFlags");
            int var8 = short.getInt(else(var7));
            long var9 = else(return(var2, "_name"));
            long var11 = else(return(var2, "_addr"));

            for(int var13 = 0; var13 < var8 - 1; ++var13) {
               long var14 = var5 + (long)(var13 * var3);
               long var16 = short.getAddress(var14 + var11);
               long var18 = short.getAddress(var14 + var9);
               String var20 = do(var18);
               if (var20 != null && var20.startsWith("BytecodeVerification")) {
                  short.putByte(var16, (byte)0);
               }
            }

         }
      }
   }
}
