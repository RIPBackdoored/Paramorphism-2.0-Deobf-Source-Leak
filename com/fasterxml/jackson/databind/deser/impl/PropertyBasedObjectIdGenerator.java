package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator$IdKey;
import com.fasterxml.jackson.annotation.ObjectIdGenerators$PropertyGenerator;

public class PropertyBasedObjectIdGenerator extends ObjectIdGenerators$PropertyGenerator {
   private static final long serialVersionUID = 1L;

   public PropertyBasedObjectIdGenerator(Class var1) {
      super(var1);
   }

   public Object generateId(Object var1) {
      throw new UnsupportedOperationException();
   }

   public ObjectIdGenerator forScope(Class var1) {
      return var1 == this._scope ? this : new PropertyBasedObjectIdGenerator(var1);
   }

   public ObjectIdGenerator newForSerialization(Object var1) {
      return this;
   }

   public ObjectIdGenerator$IdKey key(Object var1) {
      return var1 == null ? null : new ObjectIdGenerator$IdKey(this.getClass(), this._scope, var1);
   }
}
