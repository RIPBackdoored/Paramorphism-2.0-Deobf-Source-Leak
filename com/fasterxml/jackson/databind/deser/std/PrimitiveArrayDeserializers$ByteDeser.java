package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.util.ArrayBuilders$ByteBuilder;
import java.io.IOException;
import java.util.Arrays;

@JacksonStdImpl
final class PrimitiveArrayDeserializers$ByteDeser extends PrimitiveArrayDeserializers {
   private static final long serialVersionUID = 1L;

   public PrimitiveArrayDeserializers$ByteDeser() {
      super(byte[].class);
   }

   protected PrimitiveArrayDeserializers$ByteDeser(PrimitiveArrayDeserializers$ByteDeser var1, NullValueProvider var2, Boolean var3) {
      super(var1, var2, var3);
   }

   protected PrimitiveArrayDeserializers withResolved(NullValueProvider var1, Boolean var2) {
      return new PrimitiveArrayDeserializers$ByteDeser(this, var1, var2);
   }

   protected byte[] _constructEmpty() {
      return new byte[0];
   }

   public byte[] deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var3 = var1.getCurrentToken();
      if (var3 == JsonToken.VALUE_STRING) {
         label63: {
            byte[] var10000;
            try {
               var10000 = var1.getBinaryValue(var2.getBase64Variant());
            } catch (JsonParseException var9) {
               String var5 = var9.getOriginalMessage();
               if (!var5.contains("base64")) {
                  break label63;
               }

               return (byte[])((byte[])var2.handleWeirdStringValue(byte[].class, var1.getText(), var5));
            }

            return var10000;
         }
      }

      if (var3 == JsonToken.VALUE_EMBEDDED_OBJECT) {
         Object var4 = var1.getEmbeddedObject();
         if (var4 == null) {
            return null;
         }

         if (var4 instanceof byte[]) {
            return (byte[])((byte[])var4);
         }
      }

      if (!var1.isExpectedStartArrayToken()) {
         return (byte[])this.handleNonArray(var1, var2);
      } else {
         ArrayBuilders$ByteBuilder var10 = var2.getArrayBuilders().getByteBuilder();
         byte[] var11 = (byte[])var10.resetAndStart();
         int var6 = 0;

         try {
            while((var3 = var1.nextToken()) != JsonToken.END_ARRAY) {
               byte var7;
               if (var3 != JsonToken.VALUE_NUMBER_INT && var3 != JsonToken.VALUE_NUMBER_FLOAT) {
                  if (var3 == JsonToken.VALUE_NULL) {
                     if (this._nuller != null) {
                        this._nuller.getNullValue(var2);
                        continue;
                     }

                     this._verifyNullForPrimitive(var2);
                     var7 = 0;
                  } else {
                     var7 = this._parseBytePrimitive(var1, var2);
                  }
               } else {
                  var7 = var1.getByteValue();
               }

               if (var6 >= var11.length) {
                  var11 = (byte[])var10.appendCompletedChunk(var11, var6);
                  var6 = 0;
               }

               var11[var6++] = var7;
            }
         } catch (Exception var8) {
            throw JsonMappingException.wrapWithPath(var8, var11, var10.bufferedSize() + var6);
         }

         return (byte[])var10.completeAndClearBuffer(var11, var6);
      }
   }

   protected byte[] handleSingleElementUnwrapped(JsonParser var1, DeserializationContext var2) throws IOException {
      JsonToken var4 = var1.getCurrentToken();
      byte var3;
      if (var4 != JsonToken.VALUE_NUMBER_INT && var4 != JsonToken.VALUE_NUMBER_FLOAT) {
         if (var4 == JsonToken.VALUE_NULL) {
            if (this._nuller != null) {
               this._nuller.getNullValue(var2);
               return (byte[])((byte[])this.getEmptyValue(var2));
            }

            this._verifyNullForPrimitive(var2);
            return null;
         }

         Number var5 = (Number)var2.handleUnexpectedToken(this._valueClass.getComponentType(), var1);
         var3 = var5.byteValue();
      } else {
         var3 = var1.getByteValue();
      }

      return new byte[]{var3};
   }

   protected byte[] _concat(byte[] var1, byte[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      byte[] var5 = Arrays.copyOf(var1, var3 + var4);
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
      return this._concat((byte[])var1, (byte[])var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
