package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators$Base;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;

public class SimpleValueInstantiators extends ValueInstantiators$Base implements Serializable {
   private static final long serialVersionUID = -8929386427526115130L;
   protected HashMap _classMappings = new HashMap();

   public SimpleValueInstantiators() {
      super();
   }

   public SimpleValueInstantiators addValueInstantiator(Class var1, ValueInstantiator var2) {
      this._classMappings.put(new ClassKey(var1), var2);
      return this;
   }

   public ValueInstantiator findValueInstantiator(DeserializationConfig var1, BeanDescription var2, ValueInstantiator var3) {
      ValueInstantiator var4 = (ValueInstantiator)this._classMappings.get(new ClassKey(var2.getBeanClass()));
      return var4 == null ? var3 : var4;
   }
}
