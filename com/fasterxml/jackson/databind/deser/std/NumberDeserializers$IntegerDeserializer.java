package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;

@JacksonStdImpl
public final class NumberDeserializers$IntegerDeserializer extends NumberDeserializers$PrimitiveOrWrapperDeserializer {
   private static final long serialVersionUID = 1L;
   static final NumberDeserializers$IntegerDeserializer primitiveInstance;
   static final NumberDeserializers$IntegerDeserializer wrapperInstance;

   public NumberDeserializers$IntegerDeserializer(Class var1, Integer var2) {
      super(var1, var2, 0);
   }

   public boolean isCachable() {
      return true;
   }

   public Integer deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      return var1.hasToken(JsonToken.VALUE_NUMBER_INT) ? var1.getIntValue() : this._parseInteger(var1, var2);
   }

   public Integer deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var1.hasToken(JsonToken.VALUE_NUMBER_INT) ? var1.getIntValue() : this._parseInteger(var1, var2);
   }

   protected final Integer _parseInteger(JsonParser var1, DeserializationContext var2) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 3:
         return (Integer)this._deserializeFromArray(var1, var2);
      case 4:
      case 5:
      case 9:
      case 10:
      default:
         return (Integer)var2.handleUnexpectedToken(this._valueClass, var1);
      case 6:
         String var3 = var1.getText().trim();
         int var4 = var3.length();
         if (var4 == 0) {
            return (Integer)this._coerceEmptyString(var2, this._primitive);
         } else if (this._hasTextualNull(var3)) {
            return (Integer)this._coerceTextualNull(var2, this._primitive);
         } else {
            this._verifyStringForScalarCoercion(var2, var3);

            Integer var10000;
            try {
               if (var4 <= 9) {
                  var10000 = NumberInput.parseInt(var3);
                  return var10000;
               }

               long var5 = Long.parseLong(var3);
               if (!this._intOverflow(var5)) {
                  var10000 = (int)var5;
                  return var10000;
               }

               var10000 = (Integer)var2.handleWeirdStringValue(this._valueClass, var3, String.format("Overflow: numeric value (%s) out of range of Integer (%d - %d)", var3, Integer.MIN_VALUE, 0));
            } catch (IllegalArgumentException var7) {
               return (Integer)var2.handleWeirdStringValue(this._valueClass, var3, "not a valid Integer value");
            }

            return var10000;
         }
      case 7:
         return var1.getIntValue();
      case 8:
         if (!var2.isEnabled(DeserializationFeature.ACCEPT_FLOAT_AS_INT)) {
            this._failDoubleToIntCoercion(var1, var2, "Integer");
         }

         return var1.getValueAsInt();
      case 11:
         return (Integer)this._coerceNullToken(var2, this._primitive);
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
      primitiveInstance = new NumberDeserializers$IntegerDeserializer(Integer.TYPE, 0);
      wrapperInstance = new NumberDeserializers$IntegerDeserializer(Integer.class, (Integer)null);
   }
}
