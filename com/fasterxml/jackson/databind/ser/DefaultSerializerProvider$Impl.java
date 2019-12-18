package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;

public final class DefaultSerializerProvider$Impl extends DefaultSerializerProvider {
   private static final long serialVersionUID = 1L;

   public DefaultSerializerProvider$Impl() {
      super();
   }

   public DefaultSerializerProvider$Impl(DefaultSerializerProvider$Impl var1) {
      super(var1);
   }

   protected DefaultSerializerProvider$Impl(SerializerProvider var1, SerializationConfig var2, SerializerFactory var3) {
      super(var1, var2, var3);
   }

   public DefaultSerializerProvider copy() {
      return (DefaultSerializerProvider)(this.getClass() != DefaultSerializerProvider$Impl.class ? super.copy() : new DefaultSerializerProvider$Impl(this));
   }

   public DefaultSerializerProvider$Impl createInstance(SerializationConfig var1, SerializerFactory var2) {
      return new DefaultSerializerProvider$Impl(this, var1, var2);
   }

   public DefaultSerializerProvider createInstance(SerializationConfig var1, SerializerFactory var2) {
      return this.createInstance(var1, var2);
   }
}
