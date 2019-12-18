package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;

public class ValueInstantiators$Base implements ValueInstantiators {
   public ValueInstantiators$Base() {
      super();
   }

   public ValueInstantiator findValueInstantiator(DeserializationConfig var1, BeanDescription var2, ValueInstantiator var3) {
      return var3;
   }
}
