package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;

@JacksonStdImpl
public final class NumberDeserializers$BooleanDeserializer extends NumberDeserializers$PrimitiveOrWrapperDeserializer {
   private static final long serialVersionUID = 1L;
   static final NumberDeserializers$BooleanDeserializer primitiveInstance;
   static final NumberDeserializers$BooleanDeserializer wrapperInstance;

   public NumberDeserializers$BooleanDeserializer(Class var1, Boolean var2) {
      super(var1, var2, Boolean.FALSE);
   }

   public Boolean deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.VALUE_TRUE) {
         return Boolean.TRUE;
      } else {
         return var3 == JsonToken.VALUE_FALSE ? Boolean.FALSE : this._parseBoolean(var1, var2);
      }
   }

   public Boolean deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      JsonToken var4 = var1.getCurrentToken();
      if (var4 == JsonToken.VALUE_TRUE) {
         return Boolean.TRUE;
      } else {
         return var4 == JsonToken.VALUE_FALSE ? Boolean.FALSE : this._parseBoolean(var1, var2);
      }
   }

   protected final Boolean _parseBoolean(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.VALUE_NULL) {
         return (Boolean)this._coerceNullToken(var2, this._primitive);
      } else if (var3 == JsonToken.START_ARRAY) {
         return (Boolean)this._deserializeFromArray(var1, var2);
      } else if (var3 == JsonToken.VALUE_NUMBER_INT) {
         return this._parseBooleanFromInt(var1, var2);
      } else if (var3 == JsonToken.VALUE_STRING) {
         String var4 = var1.getText().trim();
         if (!"true".equals(var4) && !"True".equals(var4)) {
            if (!"false".equals(var4) && !"False".equals(var4)) {
               if (var4.length() == 0) {
                  return (Boolean)this._coerceEmptyString(var2, this._primitive);
               } else {
                  return this._hasTextualNull(var4) ? (Boolean)this._coerceTextualNull(var2, this._primitive) : (Boolean)var2.handleWeirdStringValue(this._valueClass, var4, "only \"true\" or \"false\" recognized");
               }
            } else {
               this._verifyStringForScalarCoercion(var2, var4);
               return Boolean.FALSE;
            }
         } else {
            this._verifyStringForScalarCoercion(var2, var4);
            return Boolean.TRUE;
         }
      } else if (var3 == JsonToken.VALUE_TRUE) {
         return Boolean.TRUE;
      } else {
         return var3 == JsonToken.VALUE_FALSE ? Boolean.FALSE : (Boolean)var2.handleUnexpectedToken(this._valueClass, var1);
      }
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return super.getEmptyValue(var1);
   }

   public AccessPattern getNullAccessPattern() {
      return super.getNullAccessPattern();
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return this.deserializeWithType(var1, var2, var3);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }

   static {
      primitiveInstance = new NumberDeserializers$BooleanDeserializer(Boolean.TYPE, Boolean.FALSE);
      wrapperInstance = new NumberDeserializers$BooleanDeserializer(Boolean.class, (Boolean)null);
   }
}
