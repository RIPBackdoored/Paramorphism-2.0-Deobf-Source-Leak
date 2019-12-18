package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.util.ClassUtil;

public final class DefaultDeserializationContext$Impl extends DefaultDeserializationContext {
   private static final long serialVersionUID = 1L;

   public DefaultDeserializationContext$Impl(DeserializerFactory var1) {
      super((DeserializerFactory)var1, (DeserializerCache)null);
   }

   protected DefaultDeserializationContext$Impl(DefaultDeserializationContext$Impl var1, DeserializationConfig var2, JsonParser var3, InjectableValues var4) {
      super(var1, var2, var3, var4);
   }

   protected DefaultDeserializationContext$Impl(DefaultDeserializationContext$Impl var1) {
      super(var1);
   }

   protected DefaultDeserializationContext$Impl(DefaultDeserializationContext$Impl var1, DeserializerFactory var2) {
      super((DefaultDeserializationContext)var1, (DeserializerFactory)var2);
   }

   public DefaultDeserializationContext copy() {
      ClassUtil.verifyMustOverride(DefaultDeserializationContext$Impl.class, this, "copy");
      return new DefaultDeserializationContext$Impl(this);
   }

   public DefaultDeserializationContext createInstance(DeserializationConfig var1, JsonParser var2, InjectableValues var3) {
      return new DefaultDeserializationContext$Impl(this, var1, var2, var3);
   }

   public DefaultDeserializationContext with(DeserializerFactory var1) {
      return new DefaultDeserializationContext$Impl(this, var1);
   }
}
