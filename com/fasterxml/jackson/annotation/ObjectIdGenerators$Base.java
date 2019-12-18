package com.fasterxml.jackson.annotation;

abstract class ObjectIdGenerators$Base extends ObjectIdGenerator {
   protected final Class _scope;

   protected ObjectIdGenerators$Base(Class var1) {
      super();
      this._scope = var1;
   }

   public final Class getScope() {
      return this._scope;
   }

   public boolean canUseFor(ObjectIdGenerator var1) {
      return var1.getClass() == this.getClass() && var1.getScope() == this._scope;
   }

   public abstract Object generateId(Object var1);
}
