package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.Converter;

public abstract class HandlerInstantiator {
   public HandlerInstantiator() {
      super();
   }

   public abstract JsonDeserializer deserializerInstance(DeserializationConfig var1, Annotated var2, Class var3);

   public abstract KeyDeserializer keyDeserializerInstance(DeserializationConfig var1, Annotated var2, Class var3);

   public abstract JsonSerializer serializerInstance(SerializationConfig var1, Annotated var2, Class var3);

   public abstract TypeResolverBuilder typeResolverBuilderInstance(MapperConfig var1, Annotated var2, Class var3);

   public abstract TypeIdResolver typeIdResolverInstance(MapperConfig var1, Annotated var2, Class var3);

   public ValueInstantiator valueInstantiatorInstance(MapperConfig var1, Annotated var2, Class var3) {
      return null;
   }

   public ObjectIdGenerator objectIdGeneratorInstance(MapperConfig var1, Annotated var2, Class var3) {
      return null;
   }

   public ObjectIdResolver resolverIdGeneratorInstance(MapperConfig var1, Annotated var2, Class var3) {
      return null;
   }

   public PropertyNamingStrategy namingStrategyInstance(MapperConfig var1, Annotated var2, Class var3) {
      return null;
   }

   public Converter converterInstance(MapperConfig var1, Annotated var2, Class var3) {
      return null;
   }

   public VirtualBeanPropertyWriter virtualPropertyWriterInstance(MapperConfig var1, Class var2) {
      return null;
   }

   public Object includeFilterInstance(SerializationConfig var1, BeanPropertyDefinition var2, Class var3) {
      return null;
   }
}
