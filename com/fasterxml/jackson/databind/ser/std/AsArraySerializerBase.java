package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat$Feature;
import com.fasterxml.jackson.annotation.JsonFormat$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap$SerializerAndMapResult;
import java.io.IOException;
import java.lang.reflect.Type;

public abstract class AsArraySerializerBase extends ContainerSerializer implements ContextualSerializer {
   protected final JavaType _elementType;
   protected final BeanProperty _property;
   protected final boolean _staticTyping;
   protected final Boolean _unwrapSingle;
   protected final TypeSerializer _valueTypeSerializer;
   protected final JsonSerializer _elementSerializer;
   protected PropertySerializerMap _dynamicSerializers;

   protected AsArraySerializerBase(Class var1, JavaType var2, boolean var3, TypeSerializer var4, JsonSerializer var5) {
      super(var1, false);
      this._elementType = var2;
      this._staticTyping = var3 || var2 != null && var2.isFinal();
      this._valueTypeSerializer = var4;
      this._property = null;
      this._elementSerializer = var5;
      this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
      this._unwrapSingle = null;
   }

   /** @deprecated */
   @Deprecated
   protected AsArraySerializerBase(Class var1, JavaType var2, boolean var3, TypeSerializer var4, BeanProperty var5, JsonSerializer var6) {
      super(var1, false);
      this._elementType = var2;
      this._staticTyping = var3 || var2 != null && var2.isFinal();
      this._valueTypeSerializer = var4;
      this._property = var5;
      this._elementSerializer = var6;
      this._dynamicSerializers = PropertySerializerMap.emptyForProperties();
      this._unwrapSingle = null;
   }

   protected AsArraySerializerBase(AsArraySerializerBase var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, Boolean var5) {
      super((ContainerSerializer)var1);
      this._elementType = var1._elementType;
      this._staticTyping = var1._staticTyping;
      this._valueTypeSerializer = var3;
      this._property = var2;
      this._elementSerializer = var4;
      this._dynamicSerializers = var1._dynamicSerializers;
      this._unwrapSingle = var5;
   }

   /** @deprecated */
   @Deprecated
   protected AsArraySerializerBase(AsArraySerializerBase var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4) {
      this(var1, var2, var3, var4, var1._unwrapSingle);
   }

   /** @deprecated */
   @Deprecated
   public final AsArraySerializerBase withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3) {
      return this.withResolved(var1, var2, var3, this._unwrapSingle);
   }

   public abstract AsArraySerializerBase withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer var3, Boolean var4);

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      TypeSerializer var3 = this._valueTypeSerializer;
      if (var3 != null) {
         var3 = var3.forProperty(var2);
      }

      JsonSerializer var4 = null;
      Boolean var5 = null;
      if (var2 != null) {
         AnnotationIntrospector var6 = var1.getAnnotationIntrospector();
         AnnotatedMember var7 = var2.getMember();
         if (var7 != null) {
            Object var8 = var6.findContentSerializer(var7);
            if (var8 != null) {
               var4 = var1.serializerInstance(var7, var8);
            }
         }
      }

      JsonFormat$Value var9 = this.findFormatOverrides(var1, var2, this.handledType());
      if (var9 != null) {
         var5 = var9.getFeature(JsonFormat$Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
      }

      if (var4 == null) {
         var4 = this._elementSerializer;
      }

      var4 = this.findContextualConvertingSerializer(var1, var2, var4);
      if (var4 == null && this._elementType != null && this._staticTyping && !this._elementType.isJavaLangObject()) {
         var4 = var1.findValueSerializer(this._elementType, var2);
      }

      return var4 == this._elementSerializer && var2 == this._property && this._valueTypeSerializer == var3 && this._unwrapSingle == var5 ? this : this.withResolved(var2, var3, var4, var5);
   }

   public JavaType getContentType() {
      return this._elementType;
   }

   public JsonSerializer getContentSerializer() {
      return this._elementSerializer;
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var3.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && this.hasSingleElement(var1)) {
         this.serializeContents(var1, var2, var3);
      } else {
         var2.writeStartArray();
         var2.setCurrentValue(var1);
         this.serializeContents(var1, var2, var3);
         var2.writeEndArray();
      }
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var2.setCurrentValue(var1);
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.START_ARRAY));
      this.serializeContents(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   protected abstract void serializeContents(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException;

   public JsonNode getSchema(SerializerProvider var1, Type var2) throws JsonMappingException {
      ObjectNode var3 = this.createSchemaNode("array", true);
      if (this._elementSerializer != null) {
         JsonNode var4 = null;
         if (this._elementSerializer instanceof SchemaAware) {
            var4 = ((SchemaAware)this._elementSerializer).getSchema(var1, (Type)null);
         }

         if (var4 == null) {
            var4 = JsonSchema.getDefaultSchemaNode();
         }

         var3.set("items", var4);
      }

      return var3;
   }

   public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper var1, JavaType var2) throws JsonMappingException {
      JsonSerializer var3 = this._elementSerializer;
      if (var3 == null && this._elementType != null) {
         var3 = var1.getProvider().findValueSerializer(this._elementType, this._property);
      }

      this.visitArrayFormat(var1, var2, var3, this._elementType);
   }

   protected final JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, Class var2, SerializerProvider var3) throws JsonMappingException {
      PropertySerializerMap$SerializerAndMapResult var4 = var1.findAndAddSecondarySerializer(var2, var3, this._property);
      if (var1 != var4.map) {
         this._dynamicSerializers = var4.map;
      }

      return var4.serializer;
   }

   protected final JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, JavaType var2, SerializerProvider var3) throws JsonMappingException {
      PropertySerializerMap$SerializerAndMapResult var4 = var1.findAndAddSecondarySerializer(var2, var3, this._property);
      if (var1 != var4.map) {
         this._dynamicSerializers = var4.map;
      }

      return var4.serializer;
   }
}
