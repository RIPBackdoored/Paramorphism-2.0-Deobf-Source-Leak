package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.util.ArrayBuilders$IntBuilder;
import java.io.IOException;
import java.util.Arrays;

@JacksonStdImpl
final class PrimitiveArrayDeserializers$IntDeser extends PrimitiveArrayDeserializers {
   private static final long serialVersionUID = 1L;
   public static final PrimitiveArrayDeserializers$IntDeser instance = new PrimitiveArrayDeserializers$IntDeser();

   public PrimitiveArrayDeserializers$IntDeser() {
      super(int[].class);
   }

   protected PrimitiveArrayDeserializers$IntDeser(PrimitiveArrayDeserializers$IntDeser var1, NullValueProvider var2, Boolean var3) {
      super(var1, var2, var3);
   }

   protected PrimitiveArrayDeserializers withResolved(NullValueProvider var1, Boolean var2) {
      return new PrimitiveArrayDeserializers$IntDeser(this, var1, var2);
   }

   protected int[] _constructEmpty() {
      return new int[0];
   }

   public int[] deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return (int[])this.handleNonArray(var1, var2);
      } else {
         ArrayBuilders$IntBuilder var3 = var2.getArrayBuilders().getIntBuilder();
         int[] var4 = (int[])var3.resetAndStart();
         int var5 = 0;

         JsonToken var6;
         try {
            while((var6 = var1.nextToken()) != JsonToken.END_ARRAY) {
               int var7;
               if (var6 == JsonToken.VALUE_NUMBER_INT) {
                  var7 = var1.getIntValue();
               } else if (var6 == JsonToken.VALUE_NULL) {
                  if (this._nuller != null) {
                     this._nuller.getNullValue(var2);
                     continue;
                  }

                  this._verifyNullForPrimitive(var2);
                  var7 = 0;
               } else {
                  var7 = this._parseIntPrimitive(var1, var2);
               }

               if (var5 >= var4.length) {
                  var4 = (int[])var3.appendCompletedChunk(var4, var5);
                  var5 = 0;
               }

               var4[var5++] = var7;
            }
         } catch (Exception var8) {
            throw JsonMappingException.wrapWithPath(var8, var4, var3.bufferedSize() + var5);
         }

         return (int[])var3.completeAndClearBuffer(var4, var5);
      }
   }

   protected int[] handleSingleElementUnwrapped(JsonParser var1, DeserializationContext var2) throws IOException {
      return new int[]{this._parseIntPrimitive(var1, var2)};
   }

   protected int[] _concat(int[] var1, int[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      int[] var5 = Arrays.copyOf(var1, var3 + var4);
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
      return this._concat((int[])var1, (int[])var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
