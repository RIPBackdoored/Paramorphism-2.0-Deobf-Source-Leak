package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser$NumberType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class StdSerializer extends JsonSerializer implements JsonFormatVisitable, SchemaAware, Serializable {
   private static final long serialVersionUID = 1L;
   private static final Object KEY_CONTENT_CONVERTER_LOCK = new Object();
   protected final Class _handledType;

   protected StdSerializer(Class var1) {
      super();
      this._handledType = var1;
   }

   protected StdSerializer(JavaType var1) {
      super();
      this._handledType = var1.getRawClass();
   }

   protected StdSerializer(Class var1, boolean var2) {
      super();
      this._handledType = var1;
   }

   protected StdSerializer(StdSerializer var1) {
      super();
      this._handledType = var1._handledType;
   }

   public Class handledType() {
      return this._handledType;
   }

   public abstract void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException;

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      var1.expectAnyFormat(var2);
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException {
      return this.createSchemaNode("string");
   }

   public JsonNode getSchema(SerializerProvider var1, Type var2, boolean var3) throws JsonMappingException {
      ObjectNode var4 = (ObjectNode)this.getSchema(var1, var2);
      if (!var3) {
         var4.put("required", !var3);
      }

      return var4;
   }

   protected ObjectNode createSchemaNode(String var1) {
      ObjectNode var2 = JsonNodeFactory.instance.objectNode();
      var2.put("type", var1);
      return var2;
   }

   protected ObjectNode createSchemaNode(String var1, boolean var2) {
      ObjectNode var3 = this.createSchemaNode(var1);
      if (!var2) {
         var3.put("required", !var2);
      }

      return var3;
   }

   protected void visitStringFormat(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      var1.expectStringFormat(var2);
   }

   protected void visitStringFormat(JsonFormatVisitorWrapper var1, JavaType var2, JsonValueFormat var3) throws JsonMappingException {
      JsonStringFormatVisitor var4 = var1.expectStringFormat(var2);
      if (var4 != null) {
         var4.format(var3);
      }

   }

   protected void visitIntFormat(JsonFormatVisitorWrapper var1, JavaType var2, JsonParser$NumberType var3) throws JsonMappingException {
      JsonIntegerFormatVisitor var4 = var1.expectIntegerFormat(var2);
      if (_neitherNull(var4, var3)) {
         var4.numberType(var3);
      }

   }

   protected void visitIntFormat(JsonFormatVisitorWrapper var1, JavaType var2, JsonParser$NumberType var3, JsonValueFormat var4) throws JsonMappingException {
      JsonIntegerFormatVisitor var5 = var1.expectIntegerFormat(var2);
      if (var5 != null) {
         if (var3 != null) {
            var5.numberType(var3);
         }

         if (var4 != null) {
            var5.format(var4);
         }
      }

   }

   protected void visitFloatFormat(JsonFormatVisitorWrapper var1, JavaType var2, JsonParser$NumberType var3) throws JsonMappingException {
      JsonNumberFormatVisitor var4 = var1.expectNumberFormat(var2);
      if (var4 != null) {
         var4.numberType(var3);
      }

   }

   protected void visitArrayFormat(JsonFormatVisitorWrapper var1, JavaType var2, JsonSerializer var3, JavaType var4) throws JsonMappingException {
      JsonArrayFormatVisitor var5 = var1.expectArrayFormat(var2);
      if (_neitherNull(var5, var3)) {
         var5.itemsFormat(var3, var4);
      }

   }

   protected void visitArrayFormat(JsonFormatVisitorWrapper var1, JavaType var2, JsonFormatTypes var3) throws JsonMappingException {
      JsonArrayFormatVisitor var4 = var1.expectArrayFormat(var2);
      if (var4 != null) {
         var4.itemsFormat(var3);
      }

   }

   public void wrapAndThrow(SerializerProvider var1, Throwable var2, Object var3, String var4) throws IOException {
      while(var2 instanceof InvocationTargetException && var2.getCause() != null) {
         var2 = var2.getCause();
      }

      ClassUtil.throwIfError(var2);
      boolean var5 = var1 == null || var1.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
      if (var2 instanceof IOException) {
         if (!var5 || !(var2 instanceof JsonMappingException)) {
            throw (IOException)var2;
         }
      } else if (!var5) {
         ClassUtil.throwIfRTE(var2);
      }

      throw JsonMappingException.wrapWithPath(var2, var3, var4);
   }

   public void wrapAndThrow(SerializerProvider var1, Throwable var2, Object var3, int var4) throws IOException {
      while(var2 instanceof InvocationTargetException && var2.getCause() != null) {
         var2 = var2.getCause();
      }

      ClassUtil.throwIfError(var2);
      boolean var5 = var1 == null || var1.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
      if (var2 instanceof IOException) {
         if (!var5 || !(var2 instanceof JsonMappingException)) {
            throw (IOException)var2;
         }
      } else if (!var5) {
         ClassUtil.throwIfRTE(var2);
      }

      throw JsonMappingException.wrapWithPath(var2, var3, var4);
   }

   protected JsonSerializer findContextualConvertingSerializer(SerializerProvider var1, BeanProperty var2, JsonSerializer var3) throws JsonMappingException {
      Object var4 = (Map)var1.getAttribute(KEY_CONTENT_CONVERTER_LOCK);
      if (var4 != null) {
         Object var5 = ((Map)var4).get(var2);
         if (var5 != null) {
            return var3;
         }
      } else {
         var4 = new IdentityHashMap();
         var1.setAttribute(KEY_CONTENT_CONVERTER_LOCK, var4);
      }

      ((Map)var4).put(var2, Boolean.TRUE);
      boolean var9 = false;

      JsonSerializer var6;
      label56: {
         try {
            var9 = true;
            JsonSerializer var11 = this.findConvertingContentSerializer(var1, var2, var3);
            if (var11 != null) {
               var6 = var1.handleSecondaryContextualization(var11, var2);
               var9 = false;
               break label56;
            }

            var9 = false;
         } finally {
            if (var9) {
               ((Map)var4).remove(var2);
            }
         }

         ((Map)var4).remove(var2);
         return var3;
      }

      ((Map)var4).remove(var2);
      return var6;
   }

   /** @deprecated */
   @Deprecated
   protected JsonSerializer findConvertingContentSerializer(SerializerProvider var1, BeanProperty var2, JsonSerializer var3) throws JsonMappingException {
      AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
      if (_neitherNull(var4, var2)) {
         AnnotatedMember var5 = var2.getMember();
         if (var5 != null) {
            Object var6 = var4.findSerializationContentConverter(var5);
            if (var6 != null) {
               Converter var7 = var1.converterInstance(var2.getMember(), var6);
               JavaType var8 = var7.getOutputType(var1.getTypeFactory());
               if (var3 == null && !var8.isJavaLangObject()) {
                  var3 = var1.findValueSerializer(var8);
               }

               return new StdDelegatingSerializer(var7, var8, var3);
            }
         }
      }

      return var3;
   }

   protected PropertyFilter findPropertyFilter(SerializerProvider var1, Object var2, Object var3) throws JsonMappingException {
      FilterProvider var4 = var1.getFilterProvider();
      if (var4 == null) {
         var1.reportBadDefinition(this.handledType(), "Cannot resolve PropertyFilter with id '" + var2 + "'; no FilterProvider configured");
      }

      return var4.findPropertyFilter(var2, var3);
   }

   protected JsonFormat$Value findFormatOverrides(SerializerProvider var1, BeanProperty var2, Class var3) {
      return var2 != null ? var2.findPropertyFormat(var1.getConfig(), var3) : var1.getDefaultPropertyFormat(var3);
   }

   protected Boolean findFormatFeature(SerializerProvider var1, BeanProperty var2, Class var3, JsonFormat$Feature var4) {
      JsonFormat$Value var5 = this.findFormatOverrides(var1, var2, var3);
      return var5 != null ? var5.getFeature(var4) : null;
   }

   protected JsonInclude$Value findIncludeOverrides(SerializerProvider var1, BeanProperty var2, Class var3) {
      return var2 != null ? var2.findPropertyInclusion(var1.getConfig(), var3) : var1.getDefaultPropertyInclusion(var3);
   }

   protected JsonSerializer findAnnotatedContentSerializer(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      if (var2 != null) {
         AnnotatedMember var3 = var2.getMember();
         AnnotationIntrospector var4 = var1.getAnnotationIntrospector();
         if (var3 != null) {
            Object var5 = var4.findContentSerializer(var3);
            if (var5 != null) {
               return var1.serializerInstance(var3, var5);
            }
         }
      }

      return null;
   }

   protected boolean isDefaultSerializer(JsonSerializer var1) {
      return ClassUtil.isJacksonStdImpl((Object)var1);
   }

   protected static final boolean _neitherNull(Object var0, Object var1) {
      return var0 != null && var1 != null;
   }

   protected static final boolean _nonEmpty(Collection var0) {
      return var0 != null && !var0.isEmpty();
   }
}
