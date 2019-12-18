package com.fasterxml.jackson.annotation;

public final class ObjectIdGenerators$IntSequenceGenerator extends ObjectIdGenerators$Base {
   private static final long serialVersionUID = 1L;
   protected transient int _nextValue;

   public ObjectIdGenerators$IntSequenceGenerator() {
      this(Object.class, -1);
   }

   public ObjectIdGenerators$IntSequenceGenerator(Class var1, int var2) {
      super(var1);
      this._nextValue = var2;
   }

   protected int initialValue() {
      return 1;
   }

   public ObjectIdGenerator forScope(Class var1) {
      return this._scope == var1 ? this : new ObjectIdGenerators$IntSequenceGenerator(var1, this._nextValue);
   }

   public ObjectIdGenerator newForSerialization(Object var1) {
      return new ObjectIdGenerators$IntSequenceGenerator(this._scope, this.initialValue());
   }

   public ObjectIdGenerator$IdKey key(Object var1) {
      return var1 == null ? null : new ObjectIdGenerator$IdKey(this.getClass(), this._scope, var1);
   }

   public Integer generateId(Object var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = this._nextValue++;
         return var2;
      }
   }

   public Object generateId(Object var1) {
      return this.generateId(var1);
   }

   public boolean canUseFor(ObjectIdGenerator var1) {
      return super.canUseFor(var1);
   }
}
