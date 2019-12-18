package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

public abstract class PropertyValue {
   public final PropertyValue next;
   public final Object value;

   protected PropertyValue(PropertyValue var1, Object var2) {
      super();
      this.next = var1;
      this.value = var2;
   }

   public abstract void assign(Object var1) throws IOException, JsonProcessingException;
}
