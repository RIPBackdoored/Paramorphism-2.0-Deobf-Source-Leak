package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.CompactStringObjectMap;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.IOException;

@JacksonStdImpl
public class EnumDeserializer extends StdScalarDeserializer implements ContextualDeserializer {
   private static final long serialVersionUID = 1L;
   protected Object[] _enumsByIndex;
   private final Enum _enumDefaultValue;
   protected final CompactStringObjectMap _lookupByName;
   protected CompactStringObjectMap _lookupByToString;
   protected final Boolean _caseInsensitive;

   public EnumDeserializer(EnumResolver var1, Boolean var2) {
      super(var1.getEnumClass());
      this._lookupByName = var1.constructLookup();
      this._enumsByIndex = var1.getRawEnums();
      this._enumDefaultValue = var1.getDefaultValue();
      this._caseInsensitive = var2;
   }

   protected EnumDeserializer(EnumDeserializer var1, Boolean var2) {
      super((StdScalarDeserializer)var1);
      this._lookupByName = var1._lookupByName;
      this._enumsByIndex = var1._enumsByIndex;
      this._enumDefaultValue = var1._enumDefaultValue;
      this._caseInsensitive = var2;
   }

   /** @deprecated */
   @Deprecated
   public EnumDeserializer(EnumResolver var1) {
      this((EnumResolver)var1, (Boolean)null);
   }

   /** @deprecated */
   @Deprecated
   public static JsonDeserializer deserializerForCreator(DeserializationConfig var0, Class var1, AnnotatedMethod var2) {
      return deserializerForCreator(var0, var1, var2, (ValueInstantiator)null, (SettableBeanProperty[])null);
   }

   public static JsonDeserializer deserializerForCreator(DeserializationConfig var0, Class var1, AnnotatedMethod var2, ValueInstantiator var3, SettableBeanProperty[] var4) {
      if (var0.canOverrideAccessModifiers()) {
         ClassUtil.checkAndFixAccess(var2.getMember(), var0.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
      }

      return new FactoryBasedEnumDeserializer(var1, var2, var2.getParameterType(0), var3, var4);
   }

   public static JsonDeserializer deserializerForNoArgsCreator(DeserializationConfig var0, Class var1, AnnotatedMethod var2) {
      if (var0.canOverrideAccessModifiers()) {
         ClassUtil.checkAndFixAccess(var2.getMember(), var0.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
      }

      return new FactoryBasedEnumDeserializer(var1, var2);
   }

   public EnumDeserializer withResolved(Boolean var1) {
      return this._caseInsensitive == var1 ? this : new EnumDeserializer(this, var1);
   }

   public JsonDeserializer createContextual(DeserializationContext var1, BeanProperty var2) throws JsonMappingException {
      Boolean var3 = this.findFormatFeature(var1, var2, this.handledType(), JsonFormat$Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
      if (var3 == null) {
         var3 = this._caseInsensitive;
      }

      return this.withResolved(var3);
   }

   public boolean isCachable() {
      return true;
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 != JsonToken.VALUE_STRING && var3 != JsonToken.FIELD_NAME) {
         if (var3 == JsonToken.VALUE_NUMBER_INT) {
            int var7 = var1.getIntValue();
            if (var2.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
               return var2.handleWeirdNumberValue(this._enumClass(), var7, "not allowed to deserialize Enum value out of number: disable DeserializationConfig.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS to allow");
            } else if (var7 >= 0 && var7 < this._enumsByIndex.length) {
               return this._enumsByIndex[var7];
            } else if (this._enumDefaultValue != null && var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)) {
               return this._enumDefaultValue;
            } else {
               return !var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL) ? var2.handleWeirdNumberValue(this._enumClass(), var7, "index value outside legal index range [0..%s]", this._enumsByIndex.length - 1) : null;
            }
         } else {
            return this._deserializeOther(var1, var2);
         }
      } else {
         CompactStringObjectMap var4 = var2.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING) ? this._getToStringLookup(var2) : this._lookupByName;
         String var5 = var1.getText();
         Object var6 = var4.find(var5);
         return var6 == null ? this._deserializeAltString(var1, var2, var4, var5) : var6;
      }
   }

   private final Object _deserializeAltString(JsonParser var1, DeserializationContext var2, CompactStringObjectMap var3, String var4) throws IOException {
      var4 = var4.trim();
      if (var4.length() == 0) {
         if (var2.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)) {
            return this.getEmptyValue(var2);
         }
      } else if (Boolean.TRUE.equals(this._caseInsensitive)) {
         Object var8 = var3.findCaseInsensitive(var4);
         if (var8 != null) {
            return var8;
         }
      } else if (!var2.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
         char var5 = var4.charAt(0);
         if (var5 >= '0' && var5 <= '9') {
            label68: {
               Object var10000;
               try {
                  int var6 = Integer.parseInt(var4);
                  if (var2.isEnabled(MapperFeature.ALLOW_COERCION_OF_SCALARS)) {
                     if (var6 >= 0 && var6 < this._enumsByIndex.length) {
                        var10000 = this._enumsByIndex[var6];
                        return var10000;
                     }
                     break label68;
                  }

                  var10000 = var2.handleWeirdStringValue(this._enumClass(), var4, "value looks like quoted Enum index, but `MapperFeature.ALLOW_COERCION_OF_SCALARS` prevents use");
               } catch (NumberFormatException var7) {
                  break label68;
               }

               return var10000;
            }
         }
      }

      if (this._enumDefaultValue != null && var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)) {
         return this._enumDefaultValue;
      } else if (!var2.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
         return var2.handleWeirdStringValue(this._enumClass(), var4, "value not one of declared Enum instance names: %s", var3.keys());
      } else {
         return null;
      }
   }

   protected Object _deserializeOther(JsonParser var1, DeserializationContext var2) throws IOException {
      return var1.hasToken(JsonToken.START_ARRAY) ? this._deserializeFromArray(var1, var2) : var2.handleUnexpectedToken(this._enumClass(), var1);
   }

   protected Class _enumClass() {
      return this.handledType();
   }

   protected CompactStringObjectMap _getToStringLookup(DeserializationContext var1) {
      CompactStringObjectMap var2 = this._lookupByToString;
      if (var2 == null) {
         synchronized(this) {
            var2 = EnumResolver.constructUnsafeUsingToString(this._enumClass(), var1.getAnnotationIntrospector()).constructLookup();
         }

         this._lookupByToString = var2;
      }

      return var2;
   }
}
