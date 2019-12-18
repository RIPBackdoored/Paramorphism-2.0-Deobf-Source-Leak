package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty$Bogus;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;

public class MapProperty extends PropertyWriter {
   private static final long serialVersionUID = 1L;
   private static final BeanProperty BOGUS_PROP = new BeanProperty$Bogus();
   protected final TypeSerializer _typeSerializer;
   protected final BeanProperty _property;
   protected Object _key;
   protected Object _value;
   protected JsonSerializer _keySerializer;
   protected JsonSerializer _valueSerializer;

   public MapProperty(TypeSerializer var1, BeanProperty var2) {
      super(var2 == null ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : var2.getMetadata());
      this._typeSerializer = var1;
      this._property = var2 == null ? BOGUS_PROP : var2;
   }

   public void reset(Object var1, Object var2, JsonSerializer var3, JsonSerializer var4) {
      this._key = var1;
      this._value = var2;
      this._keySerializer = var3;
      this._valueSerializer = var4;
   }

   /** @deprecated */
   @Deprecated
   public void reset(Object var1, JsonSerializer var2, JsonSerializer var3) {
      this.reset(var1, this._value, var2, var3);
   }

   public String getName() {
      return this._key instanceof String ? (String)this._key : String.valueOf(this._key);
   }

   public Object getValue() {
      return this._value;
   }

   public void setValue(Object var1) {
      this._value = var1;
   }

   public PropertyName getFullName() {
      return new PropertyName(this.getName());
   }

   public Annotation getAnnotation(Class var1) {
      return this._property.getAnnotation(var1);
   }

   public Annotation getContextAnnotation(Class var1) {
      return this._property.getContextAnnotation(var1);
   }

   public void serializeAsField(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this._keySerializer.serialize(this._key, var2, var3);
      if (this._typeSerializer == null) {
         this._valueSerializer.serialize(this._value, var2, var3);
      } else {
         this._valueSerializer.serializeWithType(this._value, var2, var3, this._typeSerializer);
      }

   }

   public void serializeAsOmittedField(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      if (!var2.canOmitFields()) {
         var2.writeOmittedField(this.getName());
      }

   }

   public void serializeAsElement(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      if (this._typeSerializer == null) {
         this._valueSerializer.serialize(this._value, var2, var3);
      } else {
         this._valueSerializer.serializeWithType(this._value, var2, var3, this._typeSerializer);
      }

   }

   public void serializeAsPlaceholder(Object var1, JsonGenerator var2, SerializerProvider var3) throws Exception {
      var2.writeNull();
   }

   public void depositSchemaProperty(JsonObjectFormatVisitor var1, SerializerProvider var2) throws JsonMappingException {
      this._property.depositSchemaProperty(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public void depositSchemaProperty(ObjectNode var1, SerializerProvider var2) throws JsonMappingException {
   }

   public JavaType getType() {
      return this._property.getType();
   }

   public PropertyName getWrapperName() {
      return this._property.getWrapperName();
   }

   public AnnotatedMember getMember() {
      return this._property.getMember();
   }
}
