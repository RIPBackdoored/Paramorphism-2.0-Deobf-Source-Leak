package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EnumResolver implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final Class _enumClass;
   protected final Enum[] _enums;
   protected final HashMap _enumsById;
   protected final Enum _defaultValue;

   protected EnumResolver(Class var1, Enum[] var2, HashMap var3, Enum var4) {
      super();
      this._enumClass = var1;
      this._enums = var2;
      this._enumsById = var3;
      this._defaultValue = var4;
   }

   public static EnumResolver constructFor(Class var0, AnnotationIntrospector var1) {
      Enum[] var2 = (Enum[])var0.getEnumConstants();
      if (var2 == null) {
         throw new IllegalArgumentException("No enum constants for class " + var0.getName());
      } else {
         String[] var3 = var1.findEnumValues(var0, var2, new String[var2.length]);
         HashMap var4 = new HashMap();
         int var5 = 0;

         for(int var6 = var2.length; var5 < var6; ++var5) {
            String var7 = var3[var5];
            if (var7 == null) {
               var7 = var2[var5].name();
            }

            var4.put(var7, var2[var5]);
         }

         Enum var8 = var1.findDefaultEnumValue(var0);
         return new EnumResolver(var0, var2, var4, var8);
      }
   }

   /** @deprecated */
   @Deprecated
   public static EnumResolver constructUsingToString(Class var0) {
      return constructUsingToString(var0, (AnnotationIntrospector)null);
   }

   public static EnumResolver constructUsingToString(Class var0, AnnotationIntrospector var1) {
      Enum[] var2 = (Enum[])var0.getEnumConstants();
      HashMap var3 = new HashMap();
      int var4 = var2.length;

      while(true) {
         --var4;
         if (var4 < 0) {
            Enum var6 = var1 == null ? null : var1.findDefaultEnumValue(var0);
            return new EnumResolver(var0, var2, var3, var6);
         }

         Enum var5 = var2[var4];
         var3.put(var5.toString(), var5);
      }
   }

   public static EnumResolver constructUsingMethod(Class var0, AnnotatedMember var1, AnnotationIntrospector var2) {
      Enum[] var3 = (Enum[])var0.getEnumConstants();
      HashMap var4 = new HashMap();
      int var5 = var3.length;

      while(true) {
         --var5;
         if (var5 < 0) {
            Enum var9 = var2 != null ? var2.findDefaultEnumValue(var0) : null;
            return new EnumResolver(var0, var3, var4, var9);
         }

         Enum var6 = var3[var5];

         try {
            Object var7 = var1.getValue(var6);
            if (var7 != null) {
               var4.put(var7.toString(), var6);
            }
         } catch (Exception var8) {
            throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + var6 + ": " + var8.getMessage());
         }
      }
   }

   public static EnumResolver constructUnsafe(Class var0, AnnotationIntrospector var1) {
      return constructFor(var0, var1);
   }

   public static EnumResolver constructUnsafeUsingToString(Class var0, AnnotationIntrospector var1) {
      return constructUsingToString(var0, var1);
   }

   public static EnumResolver constructUnsafeUsingMethod(Class var0, AnnotatedMember var1, AnnotationIntrospector var2) {
      return constructUsingMethod(var0, var1, var2);
   }

   public CompactStringObjectMap constructLookup() {
      return CompactStringObjectMap.construct(this._enumsById);
   }

   public Enum findEnum(String var1) {
      return (Enum)this._enumsById.get(var1);
   }

   public Enum getEnum(int var1) {
      return var1 >= 0 && var1 < this._enums.length ? this._enums[var1] : null;
   }

   public Enum getDefaultValue() {
      return this._defaultValue;
   }

   public Enum[] getRawEnums() {
      return this._enums;
   }

   public List getEnums() {
      ArrayList var1 = new ArrayList(this._enums.length);
      Enum[] var2 = this._enums;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Enum var5 = var2[var4];
         var1.add(var5);
      }

      return var1;
   }

   public Collection getEnumIds() {
      return this._enumsById.keySet();
   }

   public Class getEnumClass() {
      return this._enumClass;
   }

   public int lastValidIndex() {
      return this._enums.length - 1;
   }
}
