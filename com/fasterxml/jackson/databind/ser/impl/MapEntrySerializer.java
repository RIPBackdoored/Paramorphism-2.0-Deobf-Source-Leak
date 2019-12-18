package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.JsonInclude$Include;
import com.fasterxml.jackson.annotation.JsonInclude$Value;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

@JacksonStdImpl
public class MapEntrySerializer extends ContainerSerializer implements ContextualSerializer {
   public static final Object MARKER_FOR_EMPTY;
   protected final BeanProperty _property;
   protected final boolean _valueTypeIsStatic;
   protected final JavaType _entryType;
   protected final JavaType _keyType;
   protected final JavaType _valueType;
   protected JsonSerializer _keySerializer;
   protected JsonSerializer _valueSerializer;
   protected final TypeSerializer _valueTypeSerializer;
   protected PropertySerializerMap _dynamicValueSerializers;
   protected final Object _suppressableValue;
   protected final boolean _suppressNulls;

   public MapEntrySerializer(JavaType var1, JavaType var2, JavaType var3, boolean var4, TypeSerializer var5, BeanProperty var6) {
      super(var1);
      this._entryType = var1;
      this._keyType = var2;
      this._valueType = var3;
      this._valueTypeIsStatic = var4;
      this._valueTypeSerializer = var5;
      this._property = var6;
      this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
      this._suppressableValue = null;
      this._suppressNulls = false;
   }

   /** @deprecated */
   @Deprecated
   protected MapEntrySerializer(MapEntrySerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, JsonSerializer var5) {
      this(var1, var2, var3, var4, var5, var1._suppressableValue, var1._suppressNulls);
   }

   protected MapEntrySerializer(MapEntrySerializer var1, BeanProperty var2, TypeSerializer var3, JsonSerializer var4, JsonSerializer var5, Object var6, boolean var7) {
      super(Map.class, false);
      this._entryType = var1._entryType;
      this._keyType = var1._keyType;
      this._valueType = var1._valueType;
      this._valueTypeIsStatic = var1._valueTypeIsStatic;
      this._valueTypeSerializer = var1._valueTypeSerializer;
      this._keySerializer = var4;
      this._valueSerializer = var5;
      this._dynamicValueSerializers = var1._dynamicValueSerializers;
      this._property = var1._property;
      this._suppressableValue = var6;
      this._suppressNulls = var7;
   }

   public ContainerSerializer _withValueTypeSerializer(TypeSerializer var1) {
      return new MapEntrySerializer(this, this._property, var1, this._keySerializer, this._valueSerializer, this._suppressableValue, this._suppressNulls);
   }

   public MapEntrySerializer withResolved(BeanProperty var1, JsonSerializer var2, JsonSerializer var3, Object var4, boolean var5) {
      return new MapEntrySerializer(this, var1, this._valueTypeSerializer, var2, var3, var4, var5);
   }

   public MapEntrySerializer withContentInclusion(Object var1, boolean var2) {
      return this._suppressableValue == var1 && this._suppressNulls == var2 ? this : new MapEntrySerializer(this, this._property, this._valueTypeSerializer, this._keySerializer, this._valueSerializer, var1, var2);
   }

   public JsonSerializer createContextual(SerializerProvider var1, BeanProperty var2) throws JsonMappingException {
      // $FF: Couldn't be decompiled
   }

   public JavaType getContentType() {
      return this._valueType;
   }

   public JsonSerializer getContentSerializer() {
      return this._valueSerializer;
   }

   public boolean hasSingleElement(Entry var1) {
      return true;
   }

   public boolean isEmpty(SerializerProvider var1, Entry var2) {
      Object var3 = var2.getValue();
      if (var3 == null) {
         return this._suppressNulls;
      } else if (this._suppressableValue == null) {
         return false;
      } else {
         JsonSerializer var4 = this._valueSerializer;
         if (var4 == null) {
            Class var5 = var3.getClass();
            var4 = this._dynamicValueSerializers.serializerFor(var5);
            if (var4 == null) {
               try {
                  var4 = this._findAndAddDynamic(this._dynamicValueSerializers, var5, var1);
               } catch (JsonMappingException var7) {
                  return false;
               }
            }
         }

         return this._suppressableValue == MARKER_FOR_EMPTY ? var4.isEmpty(var1, var3) : this._suppressableValue.equals(var3);
      }
   }

   public void serialize(Entry var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.writeStartObject(var1);
      this.serializeDynamic(var1, var2, var3);
      var2.writeEndObject();
   }

   public void serializeWithType(Entry var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      var2.setCurrentValue(var1);
      WritableTypeId var5 = var4.writeTypePrefix(var2, var4.typeId(var1, JsonToken.START_OBJECT));
      this.serializeDynamic(var1, var2, var3);
      var4.writeTypeSuffix(var2, var5);
   }

   protected void serializeDynamic(Entry var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      TypeSerializer var4 = this._valueTypeSerializer;
      Object var5 = var1.getKey();
      JsonSerializer var6;
      if (var5 == null) {
         var6 = var3.findNullKeySerializer(this._keyType, this._property);
      } else {
         var6 = this._keySerializer;
      }

      Object var7 = var1.getValue();
      JsonSerializer var8;
      if (var7 == null) {
         if (this._suppressNulls) {
            return;
         }

         var8 = var3.getDefaultNullValueSerializer();
      } else {
         var8 = this._valueSerializer;
         if (var8 == null) {
            Class var9 = var7.getClass();
            var8 = this._dynamicValueSerializers.serializerFor(var9);
            if (var8 == null) {
               if (this._valueType.hasGenericTypes()) {
                  var8 = this._findAndAddDynamic(this._dynamicValueSerializers, var3.constructSpecializedType(this._valueType, var9), var3);
               } else {
                  var8 = this._findAndAddDynamic(this._dynamicValueSerializers, var9, var3);
               }
            }
         }

         if (this._suppressableValue != null) {
            if (this._suppressableValue == MARKER_FOR_EMPTY && var8.isEmpty(var3, var7)) {
               return;
            }

            if (this._suppressableValue.equals(var7)) {
               return;
            }
         }
      }

      var6.serialize(var5, var2, var3);

      try {
         if (var4 == null) {
            var8.serialize(var7, var2, var3);
         } else {
            var8.serializeWithType(var7, var2, var3, var4);
         }
      } catch (Exception var11) {
         String var10 = "" + var5;
         this.wrapAndThrow(var3, var11, var1, var10);
      }

   }

   protected final JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, Class var2, SerializerProvider var3) throws JsonMappingException {
      PropertySerializerMap$SerializerAndMapResult var4 = var1.findAndAddSecondarySerializer(var2, var3, this._property);
      if (var1 != var4.map) {
         this._dynamicValueSerializers = var4.map;
      }

      return var4.serializer;
   }

   protected final JsonSerializer _findAndAddDynamic(PropertySerializerMap var1, JavaType var2, SerializerProvider var3) throws JsonMappingException {
      PropertySerializerMap$SerializerAndMapResult var4 = var1.findAndAddSecondarySerializer(var2, var3, this._property);
      if (var1 != var4.map) {
         this._dynamicValueSerializers = var4.map;
      }

      return var4.serializer;
   }

   public boolean hasSingleElement(Object var1) {
      return this.hasSingleElement((Entry)var1);
   }

   public void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      this.serialize((Entry)var1, var2, var3);
   }

   public boolean isEmpty(SerializerProvider var1, Object var2) {
      return this.isEmpty(var1, (Entry)var2);
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      this.serializeWithType((Entry)var1, var2, var3, var4);
   }

   static {
      MARKER_FOR_EMPTY = JsonInclude$Include.NON_EMPTY;
   }
}
