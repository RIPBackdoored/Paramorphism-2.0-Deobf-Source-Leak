package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JsonSerializer;

final class PropertySerializerMap$Double extends PropertySerializerMap {
   private final Class _type1;
   private final Class _type2;
   private final JsonSerializer _serializer1;
   private final JsonSerializer _serializer2;

   public PropertySerializerMap$Double(PropertySerializerMap var1, Class var2, JsonSerializer var3, Class var4, JsonSerializer var5) {
      super(var1);
      this._type1 = var2;
      this._serializer1 = var3;
      this._type2 = var4;
      this._serializer2 = var5;
   }

   public JsonSerializer serializerFor(Class var1) {
      if (var1 == this._type1) {
         return this._serializer1;
      } else {
         return var1 == this._type2 ? this._serializer2 : null;
      }
   }

   public PropertySerializerMap newWith(Class var1, JsonSerializer var2) {
      PropertySerializerMap$TypeAndSerializer[] var3 = new PropertySerializerMap$TypeAndSerializer[]{new PropertySerializerMap$TypeAndSerializer(this._type1, this._serializer1), new PropertySerializerMap$TypeAndSerializer(this._type2, this._serializer2), new PropertySerializerMap$TypeAndSerializer(var1, var2)};
      return new PropertySerializerMap$Multi(this, var3);
   }
}
