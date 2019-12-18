package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

class JsonValueSerializer$TypeSerializerRerouter extends TypeSerializer {
   protected final TypeSerializer _typeSerializer;
   protected final Object _forObject;

   public JsonValueSerializer$TypeSerializerRerouter(TypeSerializer var1, Object var2) {
      super();
      this._typeSerializer = var1;
      this._forObject = var2;
   }

   public TypeSerializer forProperty(BeanProperty var1) {
      throw new UnsupportedOperationException();
   }

   public JsonTypeInfo$As getTypeInclusion() {
      return this._typeSerializer.getTypeInclusion();
   }

   public String getPropertyName() {
      return this._typeSerializer.getPropertyName();
   }

   public TypeIdResolver getTypeIdResolver() {
      return this._typeSerializer.getTypeIdResolver();
   }

   public WritableTypeId writeTypePrefix(JsonGenerator var1, WritableTypeId var2) throws IOException {
      var2.forValue = this._forObject;
      return this._typeSerializer.writeTypePrefix(var1, var2);
   }

   public WritableTypeId writeTypeSuffix(JsonGenerator var1, WritableTypeId var2) throws IOException {
      return this._typeSerializer.writeTypeSuffix(var1, var2);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForScalar(Object var1, JsonGenerator var2) throws IOException {
      this._typeSerializer.writeTypePrefixForScalar(this._forObject, var2);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForObject(Object var1, JsonGenerator var2) throws IOException {
      this._typeSerializer.writeTypePrefixForObject(this._forObject, var2);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForArray(Object var1, JsonGenerator var2) throws IOException {
      this._typeSerializer.writeTypePrefixForArray(this._forObject, var2);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypeSuffixForScalar(Object var1, JsonGenerator var2) throws IOException {
      this._typeSerializer.writeTypeSuffixForScalar(this._forObject, var2);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypeSuffixForObject(Object var1, JsonGenerator var2) throws IOException {
      this._typeSerializer.writeTypeSuffixForObject(this._forObject, var2);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypeSuffixForArray(Object var1, JsonGenerator var2) throws IOException {
      this._typeSerializer.writeTypeSuffixForArray(this._forObject, var2);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForScalar(Object var1, JsonGenerator var2, Class var3) throws IOException {
      this._typeSerializer.writeTypePrefixForScalar(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForObject(Object var1, JsonGenerator var2, Class var3) throws IOException {
      this._typeSerializer.writeTypePrefixForObject(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForArray(Object var1, JsonGenerator var2, Class var3) throws IOException {
      this._typeSerializer.writeTypePrefixForArray(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypePrefixForScalar(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._typeSerializer.writeCustomTypePrefixForScalar(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypePrefixForObject(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._typeSerializer.writeCustomTypePrefixForObject(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypePrefixForArray(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._typeSerializer.writeCustomTypePrefixForArray(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypeSuffixForScalar(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._typeSerializer.writeCustomTypeSuffixForScalar(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypeSuffixForObject(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._typeSerializer.writeCustomTypeSuffixForObject(this._forObject, var2, var3);
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypeSuffixForArray(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._typeSerializer.writeCustomTypeSuffixForArray(this._forObject, var2, var3);
   }
}
