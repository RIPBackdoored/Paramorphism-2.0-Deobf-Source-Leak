package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.IOException;

@JacksonStdImpl
final class StdKeyDeserializer$EnumKD extends StdKeyDeserializer {
   private static final long serialVersionUID = 1L;
   protected final EnumResolver _byNameResolver;
   protected final AnnotatedMethod _factory;
   protected EnumResolver _byToStringResolver;
   protected final Enum _enumDefaultValue;

   protected StdKeyDeserializer$EnumKD(EnumResolver var1, AnnotatedMethod var2) {
      super(-1, var1.getEnumClass());
      this._byNameResolver = var1;
      this._factory = var2;
      this._enumDefaultValue = var1.getDefaultValue();
   }

   public Object _parse(String var1, DeserializationContext var2) throws IOException {
      if (this._factory != null) {
         label31: {
            Object var10000;
            try {
               var10000 = this._factory.call1(var1);
            } catch (Exception var5) {
               ClassUtil.unwrapAndThrowAsIAE(var5);
               break label31;
            }

            return var10000;
         }
      }

      EnumResolver var3 = var2.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING) ? this._getToStringResolver(var2) : this._byNameResolver;
      Enum var4 = var3.findEnum(var1);
      if (var4 == null) {
         if (this._enumDefaultValue != null && var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)) {
            var4 = this._enumDefaultValue;
         } else if (!var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
            return var2.handleWeirdKey(this._keyClass, var1, "not one of values excepted for Enum class: %s", var3.getEnumIds());
         }
      }

      return var4;
   }

   private EnumResolver _getToStringResolver(DeserializationContext var1) {
      EnumResolver var2 = this._byToStringResolver;
      if (var2 == null) {
         synchronized(this) {
            var2 = EnumResolver.constructUnsafeUsingToString(this._byNameResolver.getEnumClass(), var1.getAnnotationIntrospector());
         }
      }

      return var2;
   }
}
