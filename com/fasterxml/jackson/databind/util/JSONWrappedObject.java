package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public class JSONWrappedObject implements JsonSerializable {
   protected final String _prefix;
   protected final String _suffix;
   protected final Object _value;
   protected final JavaType _serializationType;

   public JSONWrappedObject(String var1, String var2, Object var3) {
      this(var1, var2, var3, (JavaType)null);
   }

   public JSONWrappedObject(String var1, String var2, Object var3, JavaType var4) {
      super();
      this._prefix = var1;
      this._suffix = var2;
      this._value = var3;
      this._serializationType = var4;
   }

   public void serializeWithType(JsonGenerator var1, SerializerProvider var2, TypeSerializer var3) throws IOException, JsonProcessingException {
      this.serialize(var1, var2);
   }

   public void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      if (this._prefix != null) {
         var1.writeRaw(this._prefix);
      }

      if (this._value == null) {
         var2.defaultSerializeNull(var1);
      } else if (this._serializationType != null) {
         var2.findTypedValueSerializer((JavaType)this._serializationType, true, (BeanProperty)null).serialize(this._value, var1, var2);
      } else {
         Class var3 = this._value.getClass();
         var2.findTypedValueSerializer((Class)var3, true, (BeanProperty)null).serialize(this._value, var1, var2);
      }

      if (this._suffix != null) {
         var1.writeRaw(this._suffix);
      }

   }

   public String getPrefix() {
      return this._prefix;
   }

   public String getSuffix() {
      return this._suffix;
   }

   public Object getValue() {
      return this._value;
   }

   public JavaType getSerializationType() {
      return this._serializationType;
   }
}
