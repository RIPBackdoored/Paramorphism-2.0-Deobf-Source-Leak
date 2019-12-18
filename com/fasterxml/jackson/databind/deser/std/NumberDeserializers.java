package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.JsonDeserializer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;

public class NumberDeserializers {
   private static final HashSet _classNames = new HashSet();

   public NumberDeserializers() {
      super();
   }

   public static JsonDeserializer find(Class var0, String var1) {
      if (var0.isPrimitive()) {
         if (var0 == Integer.TYPE) {
            return NumberDeserializers$IntegerDeserializer.primitiveInstance;
         }

         if (var0 == Boolean.TYPE) {
            return NumberDeserializers$BooleanDeserializer.primitiveInstance;
         }

         if (var0 == Long.TYPE) {
            return NumberDeserializers$LongDeserializer.primitiveInstance;
         }

         if (var0 == Double.TYPE) {
            return NumberDeserializers$DoubleDeserializer.primitiveInstance;
         }

         if (var0 == Character.TYPE) {
            return NumberDeserializers$CharacterDeserializer.primitiveInstance;
         }

         if (var0 == Byte.TYPE) {
            return NumberDeserializers$ByteDeserializer.primitiveInstance;
         }

         if (var0 == Short.TYPE) {
            return NumberDeserializers$ShortDeserializer.primitiveInstance;
         }

         if (var0 == Float.TYPE) {
            return NumberDeserializers$FloatDeserializer.primitiveInstance;
         }
      } else {
         if (!_classNames.contains(var1)) {
            return null;
         }

         if (var0 == Integer.class) {
            return NumberDeserializers$IntegerDeserializer.wrapperInstance;
         }

         if (var0 == Boolean.class) {
            return NumberDeserializers$BooleanDeserializer.wrapperInstance;
         }

         if (var0 == Long.class) {
            return NumberDeserializers$LongDeserializer.wrapperInstance;
         }

         if (var0 == Double.class) {
            return NumberDeserializers$DoubleDeserializer.wrapperInstance;
         }

         if (var0 == Character.class) {
            return NumberDeserializers$CharacterDeserializer.wrapperInstance;
         }

         if (var0 == Byte.class) {
            return NumberDeserializers$ByteDeserializer.wrapperInstance;
         }

         if (var0 == Short.class) {
            return NumberDeserializers$ShortDeserializer.wrapperInstance;
         }

         if (var0 == Float.class) {
            return NumberDeserializers$FloatDeserializer.wrapperInstance;
         }

         if (var0 == Number.class) {
            return NumberDeserializers$NumberDeserializer.instance;
         }

         if (var0 == BigDecimal.class) {
            return NumberDeserializers$BigDecimalDeserializer.instance;
         }

         if (var0 == BigInteger.class) {
            return NumberDeserializers$BigIntegerDeserializer.instance;
         }
      }

      throw new IllegalArgumentException("Internal error: can't find deserializer for " + var0.getName());
   }

   static {
      Class[] var0 = new Class[]{Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, Number.class, BigDecimal.class, BigInteger.class};
      Class[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class var4 = var1[var3];
         _classNames.add(var4.getName());
      }

   }
}
