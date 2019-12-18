package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueDeserializer extends CollectionDeserializer {
   private static final long serialVersionUID = 1L;

   public ArrayBlockingQueueDeserializer(JavaType var1, JsonDeserializer var2, TypeDeserializer var3, ValueInstantiator var4) {
      super(var1, var2, var3, var4);
   }

   protected ArrayBlockingQueueDeserializer(JavaType var1, JsonDeserializer var2, TypeDeserializer var3, ValueInstantiator var4, JsonDeserializer var5, NullValueProvider var6, Boolean var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   protected ArrayBlockingQueueDeserializer(ArrayBlockingQueueDeserializer var1) {
      super(var1);
   }

   protected ArrayBlockingQueueDeserializer withResolved(JsonDeserializer var1, JsonDeserializer var2, TypeDeserializer var3, NullValueProvider var4, Boolean var5) {
      return new ArrayBlockingQueueDeserializer(this._containerType, var2, var3, this._valueInstantiator, var1, var4, var5);
   }

   protected Collection createDefaultInstance(DeserializationContext var1) throws IOException {
      return null;
   }

   public Collection deserialize(JsonParser var1, DeserializationContext var2, Collection var3) throws IOException {
      if (var3 != null) {
         return super.deserialize(var1, var2, var3);
      } else if (!var1.isExpectedStartArrayToken()) {
         return this.handleNonArray(var1, var2, new ArrayBlockingQueue(1));
      } else {
         var3 = super.deserialize(var1, var2, (Collection)(new ArrayList()));
         return new ArrayBlockingQueue(var3.size(), false, var3);
      }
   }

   public Object deserializeWithType(JsonParser var1, DeserializationContext var2, TypeDeserializer var3) throws IOException {
      return var3.deserializeTypedFromArray(var1, var2);
   }

   protected CollectionDeserializer withResolved(JsonDeserializer var1, JsonDeserializer var2, TypeDeserializer var3, NullValueProvider var4, Boolean var5) {
      return this.withResolved(var1, var2, var3, var4, var5);
   }

   public Object deserialize(JsonParser var1, DeserializationContext var2, Object var3) throws IOException {
      return this.deserialize(var1, var2, (Collection)var3);
   }
}
