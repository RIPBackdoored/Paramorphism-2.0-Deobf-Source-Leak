package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.util.ArrayBuilders$FloatBuilder;
import java.io.IOException;
import java.util.Arrays;

@JacksonStdImpl
final class PrimitiveArrayDeserializers$FloatDeser extends PrimitiveArrayDeserializers {
   private static final long serialVersionUID = 1L;

   public PrimitiveArrayDeserializers$FloatDeser() {
      super(float[].class);
   }

   protected PrimitiveArrayDeserializers$FloatDeser(PrimitiveArrayDeserializers$FloatDeser var1, NullValueProvider var2, Boolean var3) {
      super(var1, var2, var3);
   }

   protected PrimitiveArrayDeserializers withResolved(NullValueProvider var1, Boolean var2) {
      return new PrimitiveArrayDeserializers$FloatDeser(this, var1, var2);
   }

   protected float[] _constructEmpty() {
      return new float[0];
   }

   public float[] deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return (float[])this.handleNonArray(var1, var2);
      } else {
         ArrayBuilders$FloatBuilder var3 = var2.getArrayBuilders().getFloatBuilder();
         float[] var4 = (float[])var3.resetAndStart();
         int var5 = 0;

         JsonToken var6;
         try {
            while((var6 = var1.nextToken()) != JsonToken.END_ARRAY) {
               if (var6 == JsonToken.VALUE_NULL && this._nuller != null) {
                  this._nuller.getNullValue(var2);
               } else {
                  float var7 = this._parseFloatPrimitive(var1, var2);
                  if (var5 >= var4.length) {
                     var4 = (float[])var3.appendCompletedChunk(var4, var5);
                     var5 = 0;
                  }

                  var4[var5++] = var7;
               }
            }
         } catch (Exception var8) {
            throw JsonMappingException.wrapWithPath(var8, var4, var3.bufferedSize() + var5);
         }

         return (float[])var3.completeAndClearBuffer(var4, var5);
      }
   }

   protected float[] handleSingleElementUnwrapped(JsonParser var1, DeserializationContext var2) throws IOException {
      return new float[]{this._parseFloatPrimitive(var1, var2)};
   }

   protected float[] _concat(float[] var1, float[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      float[] var5 = Arrays.copyOf(var1, var3 + var4);
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
      return this._concat((float[])var1, (float[])var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
