package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public class AsPropertyTypeSerializer extends AsArrayTypeSerializer {
   protected final String _typePropertyName;

   public AsPropertyTypeSerializer(TypeIdResolver var1, BeanProperty var2, String var3) {
      super(var1, var2);
      this._typePropertyName = var3;
   }

   public AsPropertyTypeSerializer forProperty(BeanProperty var1) {
      return this._property == var1 ? this : new AsPropertyTypeSerializer(this._idResolver, var1, this._typePropertyName);
   }

   public String getPropertyName() {
      return this._typePropertyName;
   }

   public JsonTypeInfo$As getTypeInclusion() {
      return JsonTypeInfo$As.PROPERTY;
   }

   public AsArrayTypeSerializer forProperty(BeanProperty var1) {
      return this.forProperty(var1);
   }

   public TypeSerializer forProperty(BeanProperty var1) {
      return this.forProperty(var1);
   }
}
