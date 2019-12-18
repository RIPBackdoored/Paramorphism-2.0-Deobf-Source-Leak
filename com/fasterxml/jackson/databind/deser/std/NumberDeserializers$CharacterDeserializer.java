package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.IOException;

@JacksonStdImpl
public class NumberDeserializers$CharacterDeserializer extends NumberDeserializers$PrimitiveOrWrapperDeserializer {
   private static final long serialVersionUID = 1L;
   static final NumberDeserializers$CharacterDeserializer primitiveInstance;
   static final NumberDeserializers$CharacterDeserializer wrapperInstance;

   public NumberDeserializers$CharacterDeserializer(Class var1, Character var2) {
      super(var1, var2, '\u0000');
   }

   public Character deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      switch(var1.getCurrentTokenId()) {
      case 3:
         return (Character)this._deserializeFromArray(var1, var2);
      case 4:
      case 5:
      case 8:
      case 9:
      case 10:
      default:
         break;
      case 6:
         String var4 = var1.getText();
         if (var4.length() == 1) {
            return var4.charAt(0);
         }

         if (var4.length() == 0) {
            return (Character)this._coerceEmptyString(var2, this._primitive);
         }
         break;
      case 7:
         this._verifyNumberForScalarCoercion(var2, var1);
         int var3 = var1.getIntValue();
         if (var3 >= 0 && var3 <= 65535) {
            return (char)var3;
         }
         break;
      case 11:
         return (Character)this._coerceNullToken(var2, this._primitive);
      }

      return (Character)var2.handleUnexpectedToken(this._valueClass, var1);
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return super.getEmptyValue(var1);
   }

   public AccessPattern getNullAccessPattern() {
      return super.getNullAccessPattern();
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }

   static {
      primitiveInstance = new NumberDeserializers$CharacterDeserializer(Character.TYPE, '\u0000');
      wrapperInstance = new NumberDeserializers$CharacterDeserializer(Character.class, (Character)null);
   }
}
