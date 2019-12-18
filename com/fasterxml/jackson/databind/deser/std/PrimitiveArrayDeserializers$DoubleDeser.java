package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.util.ArrayBuilders$DoubleBuilder;
import java.io.IOException;
import java.util.Arrays;

@JacksonStdImpl
final class PrimitiveArrayDeserializers$DoubleDeser extends PrimitiveArrayDeserializers {
   private static final long serialVersionUID = 1L;

   public PrimitiveArrayDeserializers$DoubleDeser() {
      super(double[].class);
   }

   protected PrimitiveArrayDeserializers$DoubleDeser(PrimitiveArrayDeserializers$DoubleDeser var1, NullValueProvider var2, Boolean var3) {
      super(var1, var2, var3);
   }

   protected PrimitiveArrayDeserializers withResolved(NullValueProvider var1, Boolean var2) {
      return new PrimitiveArrayDeserializers$DoubleDeser(this, var1, var2);
   }

   protected double[] _constructEmpty() {
      return new double[0];
   }

   public double[] deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
      if (!var1.isExpectedStartArrayToken()) {
         return (double[])this.handleNonArray(var1, var2);
      } else {
         ArrayBuilders$DoubleBuilder var3 = var2.getArrayBuilders().getDoubleBuilder();
         double[] var4 = (double[])var3.resetAndStart();
         int var5 = 0;

         JsonToken var6;
         try {
            while((var6 = var1.nextToken()) != JsonToken.END_ARRAY) {
               if (var6 == JsonToken.VALUE_NULL && this._nuller != null) {
                  this._nuller.getNullValue(var2);
               } else {
                  double var7 = this._parseDoublePrimitive(var1, var2);
                  if (var5 >= var4.length) {
                     var4 = (double[])var3.appendCompletedChunk(var4, var5);
                     var5 = 0;
                  }

                  var4[var5++] = var7;
               }
            }
         } catch (Exception var9) {
            throw JsonMappingException.wrapWithPath(var9, var4, var3.bufferedSize() + var5);
         }

         return (double[])var3.completeAndClearBuffer(var4, var5);
      }
   }

   protected double[] handleSingleElementUnwrapped(JsonParser var1, DeserializationContext var2) throws IOException {
      return new double[]{this._parseDoublePrimitive(var1, var2)};
   }

   protected double[] _concat(double[] var1, double[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      double[] var5 = Arrays.copyOf(var1, var3 + var4);
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
      return this._concat((double[])var1, (double[])var2);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException {
      return this.deserialize(var1, var2);
   }
}
