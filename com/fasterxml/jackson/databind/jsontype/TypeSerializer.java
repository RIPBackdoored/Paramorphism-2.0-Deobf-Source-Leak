package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import java.io.IOException;

public abstract class TypeSerializer {
   public TypeSerializer() {
      super();
   }

   public abstract TypeSerializer forProperty(BeanProperty var1);

   public abstract JsonTypeInfo$As getTypeInclusion();

   public abstract String getPropertyName();

   public abstract TypeIdResolver getTypeIdResolver();

   public WritableTypeId typeId(Object var1, JsonToken var2) {
      // $FF: Couldn't be decompiled
   }

   public WritableTypeId typeId(Object var1, JsonToken var2, Object var3) {
      WritableTypeId var4 = this.typeId(var1, var2);
      var4.id = var3;
      return var4;
   }

   public WritableTypeId typeId(Object var1, Class var2, JsonToken var3) {
      WritableTypeId var4 = this.typeId(var1, var3);
      var4.forValueType = var2;
      return var4;
   }

   public abstract WritableTypeId writeTypePrefix(JsonGenerator var1, WritableTypeId var2) throws IOException;

   public abstract WritableTypeId writeTypeSuffix(JsonGenerator var1, WritableTypeId var2) throws IOException;

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForScalar(Object var1, JsonGenerator var2) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, JsonToken.VALUE_STRING));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForObject(Object var1, JsonGenerator var2) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, JsonToken.START_OBJECT));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForArray(Object var1, JsonGenerator var2) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, JsonToken.START_ARRAY));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypeSuffixForScalar(Object var1, JsonGenerator var2) throws IOException {
      this._writeLegacySuffix(var2, this.typeId(var1, JsonToken.VALUE_STRING));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypeSuffixForObject(Object var1, JsonGenerator var2) throws IOException {
      this._writeLegacySuffix(var2, this.typeId(var1, JsonToken.START_OBJECT));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypeSuffixForArray(Object var1, JsonGenerator var2) throws IOException {
      this._writeLegacySuffix(var2, this.typeId(var1, JsonToken.START_ARRAY));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForScalar(Object var1, JsonGenerator var2, Class var3) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, var3, JsonToken.VALUE_STRING));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForObject(Object var1, JsonGenerator var2, Class var3) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, var3, JsonToken.START_OBJECT));
   }

   /** @deprecated */
   @Deprecated
   public void writeTypePrefixForArray(Object var1, JsonGenerator var2, Class var3) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, var3, JsonToken.START_ARRAY));
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypePrefixForScalar(Object var1, JsonGenerator var2, String var3) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, (JsonToken)JsonToken.VALUE_STRING, (Object)var3));
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypePrefixForObject(Object var1, JsonGenerator var2, String var3) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, (JsonToken)JsonToken.START_OBJECT, (Object)var3));
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypePrefixForArray(Object var1, JsonGenerator var2, String var3) throws IOException {
      this.writeTypePrefix(var2, this.typeId(var1, (JsonToken)JsonToken.START_ARRAY, (Object)var3));
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypeSuffixForScalar(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._writeLegacySuffix(var2, this.typeId(var1, (JsonToken)JsonToken.VALUE_STRING, (Object)var3));
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypeSuffixForObject(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._writeLegacySuffix(var2, this.typeId(var1, (JsonToken)JsonToken.START_OBJECT, (Object)var3));
   }

   /** @deprecated */
   @Deprecated
   public void writeCustomTypeSuffixForArray(Object var1, JsonGenerator var2, String var3) throws IOException {
      this._writeLegacySuffix(var2, this.typeId(var1, (JsonToken)JsonToken.START_ARRAY, (Object)var3));
   }

   protected final void _writeLegacySuffix(JsonGenerator var1, WritableTypeId var2) throws IOException {
      var2.wrapperWritten = !var1.canWriteTypeId();
      this.writeTypeSuffix(var1, var2);
   }
}
