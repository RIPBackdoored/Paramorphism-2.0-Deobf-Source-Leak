package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class StdKeyDeserializers implements KeyDeserializers, Serializable {
   private static final long serialVersionUID = 1L;

   public StdKeyDeserializers() {
      super();
   }

   public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver var0) {
      return new StdKeyDeserializer$EnumKD(var0, (AnnotatedMethod)null);
   }

   public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver var0, AnnotatedMethod var1) {
      return new StdKeyDeserializer$EnumKD(var0, var1);
   }

   public static KeyDeserializer constructDelegatingKeyDeserializer(DeserializationConfig var0, JavaType var1, JsonDeserializer var2) {
      return new StdKeyDeserializer$DelegatingKD(var1.getRawClass(), var2);
   }

   public static KeyDeserializer findStringBasedKeyDeserializer(DeserializationConfig var0, JavaType var1) {
      BeanDescription var2 = var0.introspect(var1);
      Constructor var3 = var2.findSingleArgConstructor(String.class);
      if (var3 != null) {
         if (var0.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(var3, var0.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
         }

         return new StdKeyDeserializer$StringCtorKeyDeserializer(var3);
      } else {
         Method var4 = var2.findFactoryMethod(String.class);
         if (var4 != null) {
            if (var0.canOverrideAccessModifiers()) {
               ClassUtil.checkAndFixAccess(var4, var0.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
            }

            return new StdKeyDeserializer$StringFactoryKeyDeserializer(var4);
         } else {
            return null;
         }
      }
   }

   public KeyDeserializer findKeyDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) throws JsonMappingException {
      Class var4 = var1.getRawClass();
      if (var4.isPrimitive()) {
         var4 = ClassUtil.wrapperType(var4);
      }

      return StdKeyDeserializer.forType(var4);
   }
}
