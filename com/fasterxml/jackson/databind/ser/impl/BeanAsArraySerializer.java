package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException$Reference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Set;

public class BeanAsArraySerializer extends BeanSerializerBase {
   private static final long serialVersionUID = 1L;
   protected final BeanSerializerBase _defaultSerializer;

   public BeanAsArraySerializer(BeanSerializerBase var1) {
      super(var1, (ObjectIdWriter)null);
      this._defaultSerializer = var1;
   }

   protected BeanAsArraySerializer(BeanSerializerBase var1, Set var2) {
      super(var1, var2);
      this._defaultSerializer = var1;
   }

   protected BeanAsArraySerializer(BeanSerializerBase var1, ObjectIdWriter var2, Object var3) {
      super(var1, var2, var3);
      this._defaultSerializer = var1;
   }

   public JsonSerializer unwrappingSerializer(NameTransformer var1) {
      return this._defaultSerializer.unwrappingSerializer(var1);
   }

   public boolean isUnwrappingSerializer() {
      return false;
   }

   public BeanSerializerBase withObjectIdWriter(ObjectIdWriter var1) {
      return this._defaultSerializer.withObjectIdWriter(var1);
   }

   public BeanSerializerBase withFilterId(Object var1) {
      return new BeanAsArraySerializer(this, this._objectIdWriter, var1);
   }

   protected BeanAsArraySerializer withIgnorals(Set var1) {
      return new BeanAsArraySerializer(this, var1);
   }

   protected BeanSerializerBase asArraySerializer() {
      return this;
   }

   public void serializeWithType(Object var1, JsonGenerator var2, SerializerProvider var3, TypeSerializer var4) throws IOException {
      if (this._objectIdWriter != null) {
         this._serializeWithObjectId(var1, var2, var3, var4);
      } else {
         var2.setCurrentValue(var1);
         WritableTypeId var5 = this._typeIdDef(var4, var1, JsonToken.START_ARRAY);
         var4.writeTypePrefix(var2, var5);
         this.serializeAsArray(var1, var2, var3);
         var4.writeTypeSuffix(var2, var5);
      }
   }

   public final void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      if (var3.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && this.hasSingleElement(var3)) {
         this.serializeAsArray(var1, var2, var3);
      } else {
         var2.writeStartArray();
         var2.setCurrentValue(var1);
         this.serializeAsArray(var1, var2, var3);
         var2.writeEndArray();
      }
   }

   private boolean hasSingleElement(SerializerProvider var1) {
      BeanPropertyWriter[] var2;
      if (this._filteredProps != null && var1.getActiveView() != null) {
         var2 = this._filteredProps;
      } else {
         var2 = this._props;
      }

      return var2.length == 1;
   }

   protected final void serializeAsArray(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException {
      BeanPropertyWriter[] var4;
      if (this._filteredProps != null && var3.getActiveView() != null) {
         var4 = this._filteredProps;
      } else {
         var4 = this._props;
      }

      int var5 = 0;

      try {
         for(int var6 = var4.length; var5 < var6; ++var5) {
            BeanPropertyWriter var12 = var4[var5];
            if (var12 == null) {
               var2.writeNull();
            } else {
               var12.serializeAsElement(var1, var2, var3);
            }
         }
      } catch (Exception var9) {
         String var11 = var5 == var4.length ? "[anySetter]" : var4[var5].getName();
         this.wrapAndThrow(var3, var9, var1, var11);
      } catch (StackOverflowError var10) {
         JsonMappingException var7 = JsonMappingException.from((JsonGenerator)var2, "Infinite recursion (StackOverflowError)", var10);
         String var8 = var5 == var4.length ? "[anySetter]" : var4[var5].getName();
         var7.prependPath(new JsonMappingException$Reference(var1, var8));
         throw var7;
      }

   }

   public String toString() {
      return "BeanAsArraySerializer for " + this.handledType().getName();
   }

   protected BeanSerializerBase withIgnorals(Set var1) {
      return this.withIgnorals(var1);
   }

   public JsonSerializer withFilterId(Object var1) {
      return this.withFilterId(var1);
   }
}
