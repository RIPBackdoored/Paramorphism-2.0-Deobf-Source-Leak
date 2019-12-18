package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public abstract class StaticListSerializerBase extends StdSerializer implements ContextualSerializer {
   protected final Boolean _unwrapSingle;

   protected StaticListSerializerBase(Class var1) {
      super(var1, false);
      this._unwrapSingle = null;
   }

   protected StaticListSerializerBase(StaticListSerializerBase var1, Boolean var2) {
      super((StdSerializer)var1);
      this._unwrapSingle = var2;
   }

   public abstract JsonSerializer _withResolved(BeanProperty var1, Boolean var2);

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      JsonSerializer var3 = null;
      Boolean var4 = null;
      if (var2 != null) {
         AnnotationIntrospector var5 = var1.getAnnotationIntrospector();
         AnnotatedMember var6 = var2.getMember();
         if (var6 != null) {
            Object var7 = var5.findContentSerializer(var6);
            if (var7 != null) {
               var3 = var1.serializerInstance(var6, var7);
            }
         }
      }

      JsonFormat$Value var8 = this.findFormatOverrides(var1, var2, this.handledType());
      if (var8 != null) {
         var4 = var8.getFeature(JsonFormat$Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
      }

      var3 = this.findContextualConvertingSerializer(var1, var2, var3);
      if (var3 == null) {
         var3 = var1.findValueSerializer(String.class, var2);
      }

      if (this.isDefaultSerializer(var3)) {
         return (JsonSerializer)(var4 == this._unwrapSingle ? this : this._withResolved(var2, var4));
      } else {
         return new CollectionSerializer(var1.constructType(String.class), true, (TypeSerializer)null, var3);
      }
   }

   public boolean isEmpty(SerializerProvider var1, Collection var2) {
      return var2 == null || var2.size() == 0;
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) {
      return this.createSchemaNode("array", true).set("items", this.contentSchema());
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      this.acceptContentVisitor(var1.expectArrayFormat(var2));
   }

   protected abstract JsonNode contentSchema();

   protected abstract void acceptContentVisitor(JsonArrayFormatVisitor var1) throws JsonMappingException;

   public abstract void serializeWithType(Collection var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException;

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (Collection)var2);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((Collection)var1, var2, var3, var4);
   }
}
