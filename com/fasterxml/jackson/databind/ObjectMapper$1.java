package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonFactory$Feature;
import com.fasterxml.jackson.core.JsonGenerator$Feature;
import com.fasterxml.jackson.core.JsonParser$Feature;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.cfg.MutableConfigOverride;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import java.util.Collection;

class ObjectMapper$1 implements Module$SetupContext {
   final ObjectMapper this$0;

   ObjectMapper$1(ObjectMapper var1) {
      super();
      this.this$0 = var1;
   }

   public Version getMapperVersion() {
      return this.this$0.version();
   }

   public ObjectCodec getOwner() {
      return this.this$0;
   }

   public TypeFactory getTypeFactory() {
      return this.this$0._typeFactory;
   }

   public boolean isEnabled(MapperFeature var1) {
      return this.this$0.isEnabled(var1);
   }

   public boolean isEnabled(DeserializationFeature var1) {
      return this.this$0.isEnabled(var1);
   }

   public boolean isEnabled(SerializationFeature var1) {
      return this.this$0.isEnabled(var1);
   }

   public boolean isEnabled(JsonFactory$Feature var1) {
      return this.this$0.isEnabled(var1);
   }

   public boolean isEnabled(JsonParser$Feature var1) {
      return this.this$0.isEnabled(var1);
   }

   public boolean isEnabled(JsonGenerator$Feature var1) {
      return this.this$0.isEnabled(var1);
   }

   public MutableConfigOverride configOverride(Class var1) {
      return this.this$0.configOverride(var1);
   }

   public void addDeserializers(Deserializers var1) {
      DeserializerFactory var2 = this.this$0._deserializationContext._factory.withAdditionalDeserializers(var1);
      this.this$0._deserializationContext = this.this$0._deserializationContext.with(var2);
   }

   public void addKeyDeserializers(KeyDeserializers var1) {
      DeserializerFactory var2 = this.this$0._deserializationContext._factory.withAdditionalKeyDeserializers(var1);
      this.this$0._deserializationContext = this.this$0._deserializationContext.with(var2);
   }

   public void addBeanDeserializerModifier(BeanDeserializerModifier var1) {
      DeserializerFactory var2 = this.this$0._deserializationContext._factory.withDeserializerModifier(var1);
      this.this$0._deserializationContext = this.this$0._deserializationContext.with(var2);
   }

   public void addSerializers(Serializers var1) {
      this.this$0._serializerFactory = this.this$0._serializerFactory.withAdditionalSerializers(var1);
   }

   public void addKeySerializers(Serializers var1) {
      this.this$0._serializerFactory = this.this$0._serializerFactory.withAdditionalKeySerializers(var1);
   }

   public void addBeanSerializerModifier(BeanSerializerModifier var1) {
      this.this$0._serializerFactory = this.this$0._serializerFactory.withSerializerModifier(var1);
   }

   public void addAbstractTypeResolver(AbstractTypeResolver var1) {
      DeserializerFactory var2 = this.this$0._deserializationContext._factory.withAbstractTypeResolver(var1);
      this.this$0._deserializationContext = this.this$0._deserializationContext.with(var2);
   }

   public void addTypeModifier(TypeModifier var1) {
      TypeFactory var2 = this.this$0._typeFactory;
      var2 = var2.withModifier(var1);
      this.this$0.setTypeFactory(var2);
   }

   public void addValueInstantiators(ValueInstantiators var1) {
      DeserializerFactory var2 = this.this$0._deserializationContext._factory.withValueInstantiators(var1);
      this.this$0._deserializationContext = this.this$0._deserializationContext.with(var2);
   }

   public void setClassIntrospector(ClassIntrospector var1) {
      this.this$0._deserializationConfig = (DeserializationConfig)this.this$0._deserializationConfig.with((ClassIntrospector)var1);
      this.this$0._serializationConfig = (SerializationConfig)this.this$0._serializationConfig.with((ClassIntrospector)var1);
   }

   public void insertAnnotationIntrospector(AnnotationIntrospector var1) {
      this.this$0._deserializationConfig = (DeserializationConfig)this.this$0._deserializationConfig.withInsertedAnnotationIntrospector(var1);
      this.this$0._serializationConfig = (SerializationConfig)this.this$0._serializationConfig.withInsertedAnnotationIntrospector(var1);
   }

   public void appendAnnotationIntrospector(AnnotationIntrospector var1) {
      this.this$0._deserializationConfig = (DeserializationConfig)this.this$0._deserializationConfig.withAppendedAnnotationIntrospector(var1);
      this.this$0._serializationConfig = (SerializationConfig)this.this$0._serializationConfig.withAppendedAnnotationIntrospector(var1);
   }

   public void registerSubtypes(Class... var1) {
      this.this$0.registerSubtypes(var1);
   }

   public void registerSubtypes(NamedType... var1) {
      this.this$0.registerSubtypes(var1);
   }

   public void registerSubtypes(Collection var1) {
      this.this$0.registerSubtypes(var1);
   }

   public void setMixInAnnotations(Class var1, Class var2) {
      this.this$0.addMixIn(var1, var2);
   }

   public void addDeserializationProblemHandler(DeserializationProblemHandler var1) {
      this.this$0.addHandler(var1);
   }

   public void setNamingStrategy(PropertyNamingStrategy var1) {
      this.this$0.setPropertyNamingStrategy(var1);
   }
}
