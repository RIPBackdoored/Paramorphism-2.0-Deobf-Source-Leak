package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public class AsExternalTypeSerializer extends TypeSerializerBase {
   protected final String _typePropertyName;

   public AsExternalTypeSerializer(TypeIdResolver var1, BeanProperty var2, String var3) {
      super(var1, var2);
      this._typePropertyName = var3;
   }

   public AsExternalTypeSerializer forProperty(BeanProperty var1) {
      return this._property == var1 ? this : new AsExternalTypeSerializer(this._idResolver, var1, this._typePropertyName);
   }

   public String getPropertyName() {
      return this._typePropertyName;
   }

   public JsonTypeInfo$As getTypeInclusion() {
      return JsonTypeInfo$As.EXTERNAL_PROPERTY;
   }

   protected final void _writeScalarPrefix(Object var1, JsonGenerator var2) throws IOException {
   }

   protected final void _writeObjectPrefix(Object var1, JsonGenerator var2) throws IOException {
      var2.writeStartObject();
   }

   protected final void _writeArrayPrefix(Object var1, JsonGenerator var2) throws IOException {
      var2.writeStartArray();
   }

   protected final void _writeScalarSuffix(Object var1, JsonGenerator var2, String var3) throws IOException {
      if (var3 != null) {
         var2.writeStringField(this._typePropertyName, var3);
      }

   }

   protected final void _writeObjectSuffix(Object var1, JsonGenerator var2, String var3) throws IOException {
      var2.writeEndObject();
      if (var3 != null) {
         var2.writeStringField(this._typePropertyName, var3);
      }

   }

   protected final void _writeArraySuffix(Object var1, JsonGenerator var2, String var3) throws IOException {
      var2.writeEndArray();
      if (var3 != null) {
         var2.writeStringField(this._typePropertyName, var3);
      }

   }

   public TypeSerializer forProperty(BeanProperty var1) {
      return this.forProperty(var1);
   }
}
