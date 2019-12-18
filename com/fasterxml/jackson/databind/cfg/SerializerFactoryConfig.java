package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import java.io.Serializable;

public final class SerializerFactoryConfig implements Serializable {
   private static final long serialVersionUID = 1L;
   protected static final Serializers[] NO_SERIALIZERS = new Serializers[0];
   protected static final BeanSerializerModifier[] NO_MODIFIERS = new BeanSerializerModifier[0];
   protected final Serializers[] _additionalSerializers;
   protected final Serializers[] _additionalKeySerializers;
   protected final BeanSerializerModifier[] _modifiers;

   public SerializerFactoryConfig() {
      this((Serializers[])null, (Serializers[])null, (BeanSerializerModifier[])null);
   }

   protected SerializerFactoryConfig(Serializers[] var1, Serializers[] var2, BeanSerializerModifier[] var3) {
      super();
      this._additionalSerializers = var1 == null ? NO_SERIALIZERS : var1;
      this._additionalKeySerializers = var2 == null ? NO_SERIALIZERS : var2;
      this._modifiers = var3 == null ? NO_MODIFIERS : var3;
   }

   public SerializerFactoryConfig withAdditionalSerializers(Serializers var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null Serializers");
      } else {
         Serializers[] var2 = (Serializers[])ArrayBuilders.insertInListNoDup(this._additionalSerializers, var1);
         return new SerializerFactoryConfig(var2, this._additionalKeySerializers, this._modifiers);
      }
   }

   public SerializerFactoryConfig withAdditionalKeySerializers(Serializers var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null Serializers");
      } else {
         Serializers[] var2 = (Serializers[])ArrayBuilders.insertInListNoDup(this._additionalKeySerializers, var1);
         return new SerializerFactoryConfig(this._additionalSerializers, var2, this._modifiers);
      }
   }

   public SerializerFactoryConfig withSerializerModifier(BeanSerializerModifier var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null modifier");
      } else {
         BeanSerializerModifier[] var2 = (BeanSerializerModifier[])ArrayBuilders.insertInListNoDup(this._modifiers, var1);
         return new SerializerFactoryConfig(this._additionalSerializers, this._additionalKeySerializers, var2);
      }
   }

   public boolean hasSerializers() {
      return this._additionalSerializers.length > 0;
   }

   public boolean hasKeySerializers() {
      return this._additionalKeySerializers.length > 0;
   }

   public boolean hasSerializerModifiers() {
      return this._modifiers.length > 0;
   }

   public Iterable serializers() {
      return new ArrayIterator(this._additionalSerializers);
   }

   public Iterable keySerializers() {
      return new ArrayIterator(this._additionalKeySerializers);
   }

   public Iterable serializerModifiers() {
      return new ArrayIterator(this._modifiers);
   }
}
