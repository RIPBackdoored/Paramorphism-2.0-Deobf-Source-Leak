package com.fasterxml.jackson.databind;

public abstract class InjectableValues {
   public InjectableValues() {
      super();
   }

   public abstract Object findInjectableValue(Object var1, DeserializationContext var2, BeanProperty var3, Object var4) throws JsonMappingException;
}
