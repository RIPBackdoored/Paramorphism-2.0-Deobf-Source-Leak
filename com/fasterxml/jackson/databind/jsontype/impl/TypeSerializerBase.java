package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo$As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

public abstract class TypeSerializerBase extends TypeSerializer {
   protected final TypeIdResolver _idResolver;
   protected final BeanProperty _property;

   protected TypeSerializerBase(TypeIdResolver var1, BeanProperty var2) {
      super();
      this._idResolver = var1;
      this._property = var2;
   }

   public abstract JsonTypeInfo$As getTypeInclusion();

   public String getPropertyName() {
      return null;
   }

   public TypeIdResolver getTypeIdResolver() {
      return this._idResolver;
   }

   public WritableTypeId writeTypePrefix(JsonGenerator var1, WritableTypeId var2) throws IOException {
      this._generateTypeId(var2);
      return var1.writeTypePrefix(var2);
   }

   public WritableTypeId writeTypeSuffix(JsonGenerator var1, WritableTypeId var2) throws IOException {
      return var1.writeTypeSuffix(var2);
   }

   protected void _generateTypeId(WritableTypeId var1) {
      Object var2 = var1.id;
      if (var2 == null) {
         Object var3 = var1.forValue;
         Class var4 = var1.forValueType;
         String var5;
         if (var4 == null) {
            var5 = this.idFromValue(var3);
         } else {
            var5 = this.idFromValueAndType(var3, var4);
         }

         var1.id = var5;
      }

   }

   protected String idFromValue(Object var1) {
      String var2 = this._idResolver.idFromValue(var1);
      if (var2 == null) {
         this.handleMissingId(var1);
      }

      return var2;
   }

   protected String idFromValueAndType(Object var1, Class var2) {
      String var3 = this._idResolver.idFromValueAndType(var1, var2);
      if (var3 == null) {
         this.handleMissingId(var1);
      }

      return var3;
   }

   protected void handleMissingId(Object var1) {
   }
}
