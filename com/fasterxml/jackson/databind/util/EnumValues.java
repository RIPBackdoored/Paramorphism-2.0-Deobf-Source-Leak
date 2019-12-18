package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class EnumValues implements Serializable {
   private static final long serialVersionUID = 1L;
   private final Class _enumClass;
   private final Enum[] _values;
   private final SerializableString[] _textual;
   private transient EnumMap _asMap;

   private EnumValues(Class var1, SerializableString[] var2) {
      super();
      this._enumClass = var1;
      this._values = (Enum[])var1.getEnumConstants();
      this._textual = var2;
   }

   public static EnumValues construct(SerializationConfig var0, Class var1) {
      return var0.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING) ? constructFromToString(var0, var1) : constructFromName(var0, var1);
   }

   public static EnumValues constructFromName(MapperConfig var0, Class var1) {
      Class var2 = ClassUtil.findEnumType(var1);
      Enum[] var3 = (Enum[])var2.getEnumConstants();
      if (var3 == null) {
         throw new IllegalArgumentException("Cannot determine enum constants for Class " + var1.getName());
      } else {
         String[] var4 = var0.getAnnotationIntrospector().findEnumValues(var2, var3, new String[var3.length]);
         SerializableString[] var5 = new SerializableString[var3.length];
         int var6 = 0;

         for(int var7 = var3.length; var6 < var7; ++var6) {
            Enum var8 = var3[var6];
            String var9 = var4[var6];
            if (var9 == null) {
               var9 = var8.name();
            }

            var5[var8.ordinal()] = var0.compileString(var9);
         }

         return new EnumValues(var1, var5);
      }
   }

   public static EnumValues constructFromToString(MapperConfig var0, Class var1) {
      Class var2 = ClassUtil.findEnumType(var1);
      Enum[] var3 = (Enum[])var2.getEnumConstants();
      if (var3 == null) {
         throw new IllegalArgumentException("Cannot determine enum constants for Class " + var1.getName());
      } else {
         SerializableString[] var4 = new SerializableString[var3.length];
         Enum[] var5 = var3;
         int var6 = var3.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Enum var8 = var5[var7];
            var4[var8.ordinal()] = var0.compileString(var8.toString());
         }

         return new EnumValues(var1, var4);
      }
   }

   public SerializableString serializedValueFor(Enum var1) {
      return this._textual[var1.ordinal()];
   }

   public Collection values() {
      return Arrays.asList(this._textual);
   }

   public List enums() {
      return Arrays.asList(this._values);
   }

   public EnumMap internalMap() {
      EnumMap var1 = this._asMap;
      if (var1 == null) {
         LinkedHashMap var2 = new LinkedHashMap();
         Enum[] var3 = this._values;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Enum var6 = var3[var5];
            var2.put(var6, this._textual[var6.ordinal()]);
         }

         var1 = new EnumMap(var2);
      }

      return var1;
   }

   public Class getEnumClass() {
      return this._enumClass;
   }
}
