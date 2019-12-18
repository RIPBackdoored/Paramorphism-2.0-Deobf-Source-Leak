package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import java.io.IOException;

public abstract class TypeDeserializer {
   public TypeDeserializer() {
      super();
   }

   public abstract TypeDeserializer forProperty(BeanProperty var1);

   public abstract JsonTypeInfo$As getTypeInclusion();

   public abstract String getPropertyName();

   public abstract TypeIdResolver getTypeIdResolver();

   public abstract Class getDefaultImpl();

   public abstract Object deserializeTypedFromObject(JsonParser var1, DeserializationContext var2) throws IOException;

   public abstract Object deserializeTypedFromArray(JsonParser var1, DeserializationContext var2) throws IOException;

   public abstract Object deserializeTypedFromScalar(JsonParser var1, DeserializationContext var2) throws IOException;

   public abstract Object deserializeTypedFromAny(JsonParser var1, DeserializationContext var2) throws IOException;

   public static Object deserializeIfNatural(JsonParser var0, DeserializationContext var1, JavaType var2) throws IOException {
      return deserializeIfNatural(var0, var1, var2.getRawClass());
   }

   public static Object deserializeIfNatural(JsonParser var0, DeserializationContext var1, Class var2) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
