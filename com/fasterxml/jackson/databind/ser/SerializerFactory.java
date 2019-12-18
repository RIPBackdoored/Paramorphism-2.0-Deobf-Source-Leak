package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public abstract class SerializerFactory {
   public SerializerFactory() {
      super();
   }

   public abstract SerializerFactory withAdditionalSerializers(Serializers var1);

   public abstract SerializerFactory withAdditionalKeySerializers(Serializers var1);

   public abstract SerializerFactory withSerializerModifier(BeanSerializerModifier var1);

   public abstract JsonSerializer createSerializer(SerializerProvider var1, JavaType var2) throws JsonMappingException;

   public abstract TypeSerializer createTypeSerializer(SerializationConfig var1, JavaType var2) throws JsonMappingException;

   public abstract JsonSerializer createKeySerializer(SerializationConfig var1, JavaType var2, JsonSerializer var3) throws JsonMappingException;
}
