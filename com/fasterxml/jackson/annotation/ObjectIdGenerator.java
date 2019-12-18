package com.fasterxml.jackson.annotation;

import java.io.Serializable;

public abstract class ObjectIdGenerator implements Serializable {
   public ObjectIdGenerator() {
      super();
   }

   public abstract Class getScope();

   public abstract boolean canUseFor(ObjectIdGenerator var1);

   public boolean maySerializeAsObject() {
      return false;
   }

   public boolean isValidReferencePropertyName(String var1, Object var2) {
      return false;
   }

   public abstract ObjectIdGenerator forScope(Class var1);

   public abstract ObjectIdGenerator newForSerialization(Object var1);

   public abstract ObjectIdGenerator$IdKey key(Object var1);

   public abstract Object generateId(Object var1);
}
