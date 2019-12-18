package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

public class UnwrappingBeanSerializer extends BeanSerializerBase implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final NameTransformer _nameTransformer;

   public UnwrappingBeanSerializer(BeanSerializerBase var1, NameTransformer var2) {
      super(var1, var2);
      this._nameTransformer = var2;
   }

   public UnwrappingBeanSerializer(UnwrappingBeanSerializer var1, ObjectIdWriter var2) {
      super(var1, (ObjectIdWriter)var2);
      this._nameTransformer = var1._nameTransformer;
   }

   public UnwrappingBeanSerializer(UnwrappingBeanSerializer var1, ObjectIdWriter var2, Object var3) {
      super(var1, (ObjectIdWriter)var2, (Object)var3);
      this._nameTransformer = var1._nameTransformer;
   }

   protected UnwrappingBeanSerializer(UnwrappingBeanSerializer var1, Set var2) {
      super(var1, (Set)var2);
      this._nameTransformer = var1._nameTransformer;
   }

   public JsonSerializer unwrappingSerializer(NameTransformer var1) {
      return new UnwrappingBeanSerializer(this, var1);
   }

   public boolean isUnwrappingSerializer() {
      return true;
   }

   public BeanSerializerBase withObjectIdWriter(ObjectIdWriter var1) {
      return new UnwrappingBeanSerializer(this, var1);
   }

   public BeanSerializerBase withFilterId(Object var1) {
      return new UnwrappingBeanSerializer(this, this._objectIdWriter, var1);
   }

   protected BeanSerializerBase withIgnorals(Set var1) {
      return new UnwrappingBeanSerializer(this, var1);
   }

   protected BeanSerializerBase asArraySerializer() {
      return this;
   }

   public final void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      var2.setCurrentValue(var1);
      if (this._objectIdWriter != null) {
         this._serializeWithObjectId(var1, var2, var3, false);
      } else {
         if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(var1, var2, var3);
         } else {
            this.serializeFields(var1, var2, var3);
         }

      }
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      if (var3.isEnabled(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS)) {
         var3.reportBadDefinition(this.handledType(), "Unwrapped property requires use of type information: cannot serialize without disabling `SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS`");
      }

      var2.setCurrentValue(var1);
      if (this._objectIdWriter != null) {
         this._serializeWithObjectId(var1, var2, var3, var4);
      } else {
         if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(var1, var2, var3);
         } else {
            this.serializeFields(var1, var2, var3);
         }

      }
   }

   public String toString() {
      return "UnwrappingBeanSerializer for " + this.handledType().getName();
   }

   public JsonSerializer withFilterId(Object var1) {
      return this.withFilterId(var1);
   }
}
