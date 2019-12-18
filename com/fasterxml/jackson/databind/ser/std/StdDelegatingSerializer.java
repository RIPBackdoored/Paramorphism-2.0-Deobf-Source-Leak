package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.lang.reflect.Type;

public class StdDelegatingSerializer extends StdSerializer implements ContextualSerializer, ResolvableSerializer, JsonFormatVisitable, SchemaAware {
   protected final Converter _converter;
   protected final JavaType _delegateType;
   protected final JsonSerializer _delegateSerializer;

   public StdDelegatingSerializer(Converter var1) {
      super(Object.class);
      this._converter = var1;
      this._delegateType = null;
      this._delegateSerializer = null;
   }

   public StdDelegatingSerializer(Class var1, Converter var2) {
      super(var1, false);
      this._converter = var2;
      this._delegateType = null;
      this._delegateSerializer = null;
   }

   public StdDelegatingSerializer(Converter var1, JavaType var2, JsonSerializer var3) {
      super(var2);
      this._converter = var1;
      this._delegateType = var2;
      this._delegateSerializer = var3;
   }

   protected StdDelegatingSerializer withDelegate(Converter var1, JavaType var2, JsonSerializer var3) {
      ClassUtil.verifyMustOverride(StdDelegatingSerializer.class, this, "withDelegate");
      return new StdDelegatingSerializer(var1, var2, var3);
   }

   public void resolve(SerializerProvider var1) throws JsonMappingException {
      if (this._delegateSerializer != null && this._delegateSerializer instanceof ResolvableSerializer) {
         ((ResolvableSerializer)this._delegateSerializer).resolve(var1);
      }

   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = this._delegateSerializer;
      JavaType var4 = this._delegateType;
      if (var3 == null) {
         if (var4 == null) {
            var4 = this._converter.getOutputType(var1.getTypeFactory());
         }

         if (!var4.isJavaLangObject()) {
            var3 = var1.findValueSerializer(var4);
         }
      }

      if (var3 instanceof ContextualSerializer) {
         var3 = var1.handleSecondaryContextualization(var3, var2);
      }

      return var3 == this._delegateSerializer && var4 == this._delegateType ? this : this.withDelegate(this._converter, var4, var3);
   }

   protected Converter getConverter() {
      return this._converter;
   }

   public JsonSerializer getDelegatee() {
      return this._delegateSerializer;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      Object var4 = this.convertValue(var1);
      if (var4 == null) {
         var3.defaultSerializeNull(var2);
      } else {
         JsonSerializer var5 = this._delegateSerializer;
         if (var5 == null) {
            var5 = this._findSerializer(var4, var3);
         }

         var5.serialize(var4, var2, var3);
      }
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      Object var5 = this.convertValue(var1);
      JsonSerializer var6 = this._delegateSerializer;
      if (var6 == null) {
         var6 = this._findSerializer(var1, var3);
      }

      var6.serializeWithType(var5, var2, var3, var4);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      Object var3 = this.convertValue(var2);
      if (var3 == null) {
         return true;
      } else if (this._delegateSerializer == null) {
         return var2 == null;
      } else {
         return this._delegateSerializer.isEmpty(var1, var3);
      }
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException {
      return this._delegateSerializer instanceof SchemaAware ? ((SchemaAware)this._delegateSerializer).getSchema(var1, var2) : super.getSchema(var1, var2);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2, boolean var3) throws JsonMappingException {
      return this._delegateSerializer instanceof SchemaAware ? ((SchemaAware)this._delegateSerializer).getSchema(var1, var2, var3) : super.getSchema(var1, var2);
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      if (this._delegateSerializer != null) {
         this._delegateSerializer.acceptJsonFormatVisitor(var1, var2);
      }

   }

   protected Object convertValue(Object var1) {
      return this._converter.convert(var1);
   }

   protected JsonSerializer _findSerializer(Object var1, SerializerProvider var2) throws JsonMappingException {
      return var2.findValueSerializer(var1.getClass());
   }
}
