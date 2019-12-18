package com.fasterxml.jackson.annotation;

import java.util.UUID;

public final class ObjectIdGenerators$StringIdGenerator extends ObjectIdGenerators$Base {
   private static final long serialVersionUID = 1L;

   public ObjectIdGenerators$StringIdGenerator() {
      this(Object.class);
   }

   private ObjectIdGenerators$StringIdGenerator(Class var1) {
      super(Object.class);
   }

   public ObjectIdGenerator forScope(Class var1) {
      return this;
   }

   public ObjectIdGenerator newForSerialization(Object var1) {
      return this;
   }

   public String generateId(Object var1) {
      return UUID.randomUUID().toString();
   }

   public ObjectIdGenerator$IdKey key(Object var1) {
      return var1 == null ? null : new ObjectIdGenerator$IdKey(this.getClass(), (Class)null, var1);
   }

   public boolean canUseFor(ObjectIdGenerator var1) {
      return var1 instanceof ObjectIdGenerators$StringIdGenerator;
   }

   public Object generateId(Object var1) {
      return this.generateId(var1);
   }
}
