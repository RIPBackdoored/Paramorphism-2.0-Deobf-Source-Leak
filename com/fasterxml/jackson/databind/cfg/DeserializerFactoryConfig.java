package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import java.io.Serializable;

public class DeserializerFactoryConfig implements Serializable {
   private static final long serialVersionUID = 1L;
   protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];
   protected static final BeanDeserializerModifier[] NO_MODIFIERS = new BeanDeserializerModifier[0];
   protected static final AbstractTypeResolver[] NO_ABSTRACT_TYPE_RESOLVERS = new AbstractTypeResolver[0];
   protected static final ValueInstantiators[] NO_VALUE_INSTANTIATORS = new ValueInstantiators[0];
   protected static final KeyDeserializers[] DEFAULT_KEY_DESERIALIZERS = new KeyDeserializers[]{new StdKeyDeserializers()};
   protected final Deserializers[] _additionalDeserializers;
   protected final KeyDeserializers[] _additionalKeyDeserializers;
   protected final BeanDeserializerModifier[] _modifiers;
   protected final AbstractTypeResolver[] _abstractTypeResolvers;
   protected final ValueInstantiators[] _valueInstantiators;

   public DeserializerFactoryConfig() {
      this((Deserializers[])null, (KeyDeserializers[])null, (BeanDeserializerModifier[])null, (AbstractTypeResolver[])null, (ValueInstantiators[])null);
   }

   protected DeserializerFactoryConfig(Deserializers[] var1, KeyDeserializers[] var2, BeanDeserializerModifier[] var3, AbstractTypeResolver[] var4, ValueInstantiators[] var5) {
      super();
      this._additionalDeserializers = var1 == null ? NO_DESERIALIZERS : var1;
      this._additionalKeyDeserializers = var2 == null ? DEFAULT_KEY_DESERIALIZERS : var2;
      this._modifiers = var3 == null ? NO_MODIFIERS : var3;
      this._abstractTypeResolvers = var4 == null ? NO_ABSTRACT_TYPE_RESOLVERS : var4;
      this._valueInstantiators = var5 == null ? NO_VALUE_INSTANTIATORS : var5;
   }

   public DeserializerFactoryConfig withAdditionalDeserializers(Deserializers var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null Deserializers");
      } else {
         Deserializers[] var2 = (Deserializers[])ArrayBuilders.insertInListNoDup(this._additionalDeserializers, var1);
         return new DeserializerFactoryConfig(var2, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
      }
   }

   public DeserializerFactoryConfig withAdditionalKeyDeserializers(KeyDeserializers var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null KeyDeserializers");
      } else {
         KeyDeserializers[] var2 = (KeyDeserializers[])ArrayBuilders.insertInListNoDup(this._additionalKeyDeserializers, var1);
         return new DeserializerFactoryConfig(this._additionalDeserializers, var2, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
      }
   }

   public DeserializerFactoryConfig withDeserializerModifier(BeanDeserializerModifier var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null modifier");
      } else {
         BeanDeserializerModifier[] var2 = (BeanDeserializerModifier[])ArrayBuilders.insertInListNoDup(this._modifiers, var1);
         return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, var2, this._abstractTypeResolvers, this._valueInstantiators);
      }
   }

   public DeserializerFactoryConfig withAbstractTypeResolver(AbstractTypeResolver var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null resolver");
      } else {
         AbstractTypeResolver[] var2 = (AbstractTypeResolver[])ArrayBuilders.insertInListNoDup(this._abstractTypeResolvers, var1);
         return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, var2, this._valueInstantiators);
      }
   }

   public DeserializerFactoryConfig withValueInstantiators(ValueInstantiators var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Cannot pass null resolver");
      } else {
         ValueInstantiators[] var2 = (ValueInstantiators[])ArrayBuilders.insertInListNoDup(this._valueInstantiators, var1);
         return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, var2);
      }
   }

   public boolean hasDeserializers() {
      return this._additionalDeserializers.length > 0;
   }

   public boolean hasKeyDeserializers() {
      return this._additionalKeyDeserializers.length > 0;
   }

   public boolean hasDeserializerModifiers() {
      return this._modifiers.length > 0;
   }

   public boolean hasAbstractTypeResolvers() {
      return this._abstractTypeResolvers.length > 0;
   }

   public boolean hasValueInstantiators() {
      return this._valueInstantiators.length > 0;
   }

   public Iterable deserializers() {
      return new ArrayIterator(this._additionalDeserializers);
   }

   public Iterable keyDeserializers() {
      return new ArrayIterator(this._additionalKeyDeserializers);
   }

   public Iterable deserializerModifiers() {
      return new ArrayIterator(this._modifiers);
   }

   public Iterable abstractTypeResolvers() {
      return new ArrayIterator(this._abstractTypeResolvers);
   }

   public Iterable valueInstantiators() {
      return new ArrayIterator(this._valueInstantiators);
   }
}
