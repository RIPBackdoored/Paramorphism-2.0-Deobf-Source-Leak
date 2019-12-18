package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator$IdKey;
import com.fasterxml.jackson.annotation.ObjectIdGenerators$PropertyGenerator;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public class PropertyBasedObjectIdGenerator extends ObjectIdGenerators$PropertyGenerator {
   private static final long serialVersionUID = 1L;
   protected final BeanPropertyWriter _property;

   public PropertyBasedObjectIdGenerator(ObjectIdInfo var1, BeanPropertyWriter var2) {
      this(var1.getScope(), var2);
   }

   protected PropertyBasedObjectIdGenerator(Class var1, BeanPropertyWriter var2) {
      super(var1);
      this._property = var2;
   }

   public boolean canUseFor(ObjectIdGenerator var1) {
      if (var1.getClass() == this.getClass()) {
         PropertyBasedObjectIdGenerator var2 = (PropertyBasedObjectIdGenerator)var1;
         if (var2.getScope() == this._scope) {
            return var2._property == this._property;
         }
      }

      return false;
   }

   public Object generateId(Object var1) {
      Object var10000;
      try {
         var10000 = this._property.get(var1);
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new IllegalStateException("Problem accessing property '" + this._property.getName() + "': " + var4.getMessage(), var4);
      }

      return var10000;
   }

   public ObjectIdGenerator forScope(Class var1) {
      return var1 == this._scope ? this : new PropertyBasedObjectIdGenerator(var1, this._property);
   }

   public ObjectIdGenerator newForSerialization(Object var1) {
      return this;
   }

   public ObjectIdGenerator$IdKey key(Object var1) {
      return var1 == null ? null : new ObjectIdGenerator$IdKey(this.getClass(), this._scope, var1);
   }
}
