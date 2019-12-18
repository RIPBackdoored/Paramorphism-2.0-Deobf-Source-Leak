package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JsonSerializer;

final class PropertySerializerMap$Empty extends PropertySerializerMap {
   public static final PropertySerializerMap$Empty FOR_PROPERTIES = new PropertySerializerMap$Empty(false);
   public static final PropertySerializerMap$Empty FOR_ROOT_VALUES = new PropertySerializerMap$Empty(true);

   protected PropertySerializerMap$Empty(boolean var1) {
      super(var1);
   }

   public JsonSerializer serializerFor(Class var1) {
      return null;
   }

   public PropertySerializerMap newWith(Class var1, JsonSerializer var2) {
      return new PropertySerializerMap$Single(this, var1, var2);
   }
}
