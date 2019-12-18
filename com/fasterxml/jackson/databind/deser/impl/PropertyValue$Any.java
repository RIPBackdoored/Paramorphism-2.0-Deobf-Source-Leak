package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import java.io.IOException;

final class PropertyValue$Any extends PropertyValue {
   final SettableAnyProperty _property;
   final String _propertyName;

   public PropertyValue$Any(PropertyValue var1, Object var2, SettableAnyProperty var3, String var4) {
      super(var1, var2);
      this._property = var3;
      this._propertyName = var4;
   }

   public void assign(Object var1) throws IOException, JsonProcessingException {
      this._property.set(var1, this._propertyName, this.value);
   }
}
