package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Set;

public class BeanSerializer extends BeanSerializerBase {
   private static final long serialVersionUID = 29L;

   public BeanSerializer(JavaType var1, BeanSerializerBuilder var2, BeanPropertyWriter[] var3, BeanPropertyWriter[] var4) {
      super(var1, var2, var3, var4);
   }

   protected BeanSerializer(BeanSerializerBase var1) {
      super(var1);
   }

   protected BeanSerializer(BeanSerializerBase var1, ObjectIdWriter var2) {
      super(var1, var2);
   }

   protected BeanSerializer(BeanSerializerBase var1, ObjectIdWriter var2, Object var3) {
      super(var1, var2, var3);
   }

   protected BeanSerializer(BeanSerializerBase var1, Set var2) {
      super(var1, var2);
   }

   public static BeanSerializer createDummy(JavaType var0) {
      return new BeanSerializer(var0, (BeanSerializerBuilder)null, NO_PROPS, (BeanPropertyWriter[])null);
   }

   public JsonSerializer unwrappingSerializer(NameTransformer var1) {
      return new UnwrappingBeanSerializer(this, var1);
   }

   public BeanSerializerBase withObjectIdWriter(ObjectIdWriter var1) {
      return new BeanSerializer(this, var1, this._propertyFilterId);
   }

   public BeanSerializerBase withFilterId(Object var1) {
      return new BeanSerializer(this, this._objectIdWriter, var1);
   }

   protected BeanSerializerBase withIgnorals(Set var1) {
      return new BeanSerializer(this, var1);
   }

   protected BeanSerializerBase asArraySerializer() {
      return (BeanSerializerBase)(this._objectIdWriter == null && this._anyGetterWriter == null && this._propertyFilterId == null ? new BeanAsArraySerializer(this) : this);
   }

   public final void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (this._objectIdWriter != null) {
         var2.setCurrentValue(var1);
         this._serializeWithObjectId(var1, var2, var3, true);
      } else {
         var2.writeStartObject(var1);
         if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(var1, var2, var3);
         } else {
            this.serializeFields(var1, var2, var3);
         }

         var2.writeEndObject();
      }
   }

   public String toString() {
      return "BeanSerializer for " + this.handledType().getName();
   }

   public JsonSerializer withFilterId(Object var1) {
      return this.withFilterId(var1);
   }
}
