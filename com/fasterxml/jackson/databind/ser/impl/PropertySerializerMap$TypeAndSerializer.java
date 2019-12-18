package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JsonSerializer;

final class PropertySerializerMap$TypeAndSerializer {
   public final Class type;
   public final JsonSerializer serializer;

   public PropertySerializerMap$TypeAndSerializer(Class var1, JsonSerializer var2) {
      super();
      this.type = var1;
      this.serializer = var2;
   }
}
