package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.util.AccessPattern;

public abstract class NumberDeserializers$PrimitiveOrWrapperDeserializer extends StdScalarDeserializer {
   private static final long serialVersionUID = 1L;
   protected final Object _nullValue;
   protected final Object _emptyValue;
   protected final boolean _primitive;

   protected NumberDeserializers$PrimitiveOrWrapperDeserializer(Class var1, Object var2, Object var3) {
      super(var1);
      this._nullValue = var2;
      this._emptyValue = var3;
      this._primitive = var1.isPrimitive();
   }

   public AccessPattern getNullAccessPattern() {
      if (this._primitive) {
         return AccessPattern.DYNAMIC;
      } else {
         return this._nullValue == null ? AccessPattern.ALWAYS_NULL : AccessPattern.CONSTANT;
      }
   }

   public final Object getNullValue(DeserializationContext var1) throws JsonMappingException {
      if (this._primitive && var1.isEnabled(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)) {
         var1.reportInputMismatch((JsonDeserializer)this, "Cannot map `null` into type %s (set DeserializationConfig.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES to 'false' to allow)", this.handledType().toString());
      }

      return this._nullValue;
   }

   public Object getEmptyValue(DeserializationContext var1) throws JsonMappingException {
      return this._emptyValue;
   }
}
