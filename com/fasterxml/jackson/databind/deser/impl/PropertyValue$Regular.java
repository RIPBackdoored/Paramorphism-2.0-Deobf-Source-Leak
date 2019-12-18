package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import java.io.IOException;

final class PropertyValue$Regular extends PropertyValue {
   final SettableBeanProperty _property;

   public PropertyValue$Regular(PropertyValue var1, Object var2, SettableBeanProperty var3) {
      super(var1, var2);
      this._property = var3;
   }

   public void assign(Object var1) throws IOException, JsonProcessingException {
      this._property.set(var1, this.value);
   }
}
