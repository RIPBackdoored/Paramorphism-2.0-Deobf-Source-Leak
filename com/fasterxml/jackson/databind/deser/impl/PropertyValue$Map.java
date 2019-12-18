package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

final class PropertyValue$Map extends PropertyValue {
   final Object _key;

   public PropertyValue$Map(PropertyValue var1, Object var2, Object var3) {
      super(var1, var2);
      this._key = var3;
   }

   public void assign(Object var1) throws IOException, JsonProcessingException {
      ((java.util.Map)var1).put(this._key, this.value);
   }
}
