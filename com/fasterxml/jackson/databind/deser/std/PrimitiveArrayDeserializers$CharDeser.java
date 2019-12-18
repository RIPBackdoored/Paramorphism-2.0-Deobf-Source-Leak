package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import java.io.IOException;
import java.util.Arrays;

@JacksonStdImpl
final class PrimitiveArrayDeserializers$CharDeser extends PrimitiveArrayDeserializers {
   private static final long serialVersionUID = 1L;

   public PrimitiveArrayDeserializers$CharDeser() {
      super(char[].class);
   }

   protected PrimitiveArrayDeserializers$CharDeser(PrimitiveArrayDeserializers$CharDeser var1, NullValueProvider var2, Boolean var3) {
      super(var1, var2, var3);
   }

   protected PrimitiveArrayDeserializers withResolved(NullValueProvider var1, Boolean var2) {
      return this;
   }

   protected char[] _constructEmpty() {
      return new char[0];
   }

   public char[] deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.VALUE_STRING) {
         char[] var9 = var1.getTextCharacters();
         int var10 = var1.getTextOffset();
         int var11 = var1.getTextLength();
         char[] var7 = new char[var11];
         System.arraycopy(var9, var10, var7, 0, var11);
         return var7;
      } else if (!var1.isExpectedStartArrayToken()) {
         if (var3 == JsonToken.VALUE_EMBEDDED_OBJECT) {
            Object var8 = var1.getEmbeddedObject();
            if (var8 == null) {
               return null;
            }

            if (var8 instanceof char[]) {
               return (char[])((char[])var8);
            }

            if (var8 instanceof String) {
               return ((String)var8).toCharArray();
            }

            if (var8 instanceof byte[]) {
               return Base64Variants.getDefaultVariant().encode((byte[])((byte[])var8), false).toCharArray();
            }
         }

         return (char[])((char[])var2.handleUnexpectedToken(this._valueClass, var1));
      } else {
         StringBuilder var4 = new StringBuilder(64);

         while(true) {
            while((var3 = var1.nextToken()) != JsonToken.END_ARRAY) {
               String var5;
               if (var3 == JsonToken.VALUE_STRING) {
                  var5 = var1.getText();
               } else if (var3 == JsonToken.VALUE_NULL) {
                  if (this._nuller != null) {
                     this._nuller.getNullValue(var2);
                     continue;
                  }

                  this._verifyNullForPrimitive(var2);
                  var5 = "\u0000";
               } else {
                  CharSequence var6 = (CharSequence)var2.handleUnexpectedToken(Character.TYPE, var1);
                  var5 = var6.toString();
               }

               if (var5.length() != 1) {
                  var2.reportInputMismatch((JsonDeserializer)this, "Cannot convert a JSON String of length %d into a char element of char array", var5.length());
               }

               var4.append(var5.charAt(0));
            }

            return var4.toString().toCharArray();
         }
      }
   }

   protected char[] handleSingleElementUnwrapped(JsonParser var1, DeserializationContext var2) throws IOException {
      return (char[])((char[])var2.handleUnexpectedToken(this._valueClass, var1));
   }

   protected char[] _concat(char[] var1, char[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      char[] var5 = Arrays.copyOf(var1, var3 + var4);
      System.arraycopy(var2, 0, var5, var3, var4);
      return var5;
   }

   protected Object _constructEmpty() {
      return this._constructEmpty();
   }

   protected Object handleSingleElementUnwrapped(JsonParser var1, DeserializationContext var2) throws IOException {
      return this.handleSingleElementUnwrapped(var1, var2);
   }

   protected Object _concat(Object var1, Object var2) {
      return this._concat((char[])var1, (char[])var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
