package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;
import java.util.HashSet;

public final class ArrayBuilders {
   private ArrayBuilders$BooleanBuilder _booleanBuilder = null;
   private ArrayBuilders$ByteBuilder _byteBuilder = null;
   private ArrayBuilders$ShortBuilder _shortBuilder = null;
   private ArrayBuilders$IntBuilder _intBuilder = null;
   private ArrayBuilders$LongBuilder _longBuilder = null;
   private ArrayBuilders$FloatBuilder _floatBuilder = null;
   private ArrayBuilders$DoubleBuilder _doubleBuilder = null;

   public ArrayBuilders() {
      super();
   }

   public ArrayBuilders$BooleanBuilder getBooleanBuilder() {
      if (this._booleanBuilder == null) {
         this._booleanBuilder = new ArrayBuilders$BooleanBuilder();
      }

      return this._booleanBuilder;
   }

   public ArrayBuilders$ByteBuilder getByteBuilder() {
      if (this._byteBuilder == null) {
         this._byteBuilder = new ArrayBuilders$ByteBuilder();
      }

      return this._byteBuilder;
   }

   public ArrayBuilders$ShortBuilder getShortBuilder() {
      if (this._shortBuilder == null) {
         this._shortBuilder = new ArrayBuilders$ShortBuilder();
      }

      return this._shortBuilder;
   }

   public ArrayBuilders$IntBuilder getIntBuilder() {
      if (this._intBuilder == null) {
         this._intBuilder = new ArrayBuilders$IntBuilder();
      }

      return this._intBuilder;
   }

   public ArrayBuilders$LongBuilder getLongBuilder() {
      if (this._longBuilder == null) {
         this._longBuilder = new ArrayBuilders$LongBuilder();
      }

      return this._longBuilder;
   }

   public ArrayBuilders$FloatBuilder getFloatBuilder() {
      if (this._floatBuilder == null) {
         this._floatBuilder = new ArrayBuilders$FloatBuilder();
      }

      return this._floatBuilder;
   }

   public ArrayBuilders$DoubleBuilder getDoubleBuilder() {
      if (this._doubleBuilder == null) {
         this._doubleBuilder = new ArrayBuilders$DoubleBuilder();
      }

      return this._doubleBuilder;
   }

   public static Object getArrayComparator(Object var0) {
      int var1 = Array.getLength(var0);
      Class var2 = var0.getClass();
      return new ArrayBuilders$1(var2, var1, var0);
   }

   public static HashSet arrayToSet(Object[] var0) {
      if (var0 == null) {
         return new HashSet();
      } else {
         int var1 = var0.length;
         HashSet var2 = new HashSet(var1);

         for(int var3 = 0; var3 < var1; ++var3) {
            var2.add(var0[var3]);
         }

         return var2;
      }
   }

   public static Object[] insertInListNoDup(Object[] var0, Object var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var0[var3] == var1) {
            if (var3 == 0) {
               return var0;
            }

            Object[] var4 = (Object[])((Object[])Array.newInstance(var0.getClass().getComponentType(), var2));
            System.arraycopy(var0, 0, var4, 1, var3);
            var4[0] = var1;
            ++var3;
            int var5 = var2 - var3;
            if (var5 > 0) {
               System.arraycopy(var0, var3, var4, var3, var5);
            }

            return var4;
         }
      }

      Object[] var6 = (Object[])((Object[])Array.newInstance(var0.getClass().getComponentType(), var2 + 1));
      if (var2 > 0) {
         System.arraycopy(var0, 0, var6, 1, var2);
      }

      var6[0] = var1;
      return var6;
   }
}
