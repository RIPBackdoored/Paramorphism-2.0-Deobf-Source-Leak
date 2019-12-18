package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class ContainerSerializer extends StdSerializer {
   protected ContainerSerializer(Class var1) {
      super(var1);
   }

   protected ContainerSerializer(JavaType var1) {
      super(var1);
   }

   protected ContainerSerializer(Class var1, boolean var2) {
      super(var1, var2);
   }

   protected ContainerSerializer(ContainerSerializer var1) {
      super(var1._handledType, false);
   }

   public ContainerSerializer withValueTypeSerializer(TypeSerializer var1) {
      return var1 == null ? this : this._withValueTypeSerializer(var1);
   }

   public abstract JavaType getContentType();

   public abstract JsonSerializer getContentSerializer();

   public abstract boolean hasSingleElement(Object var1);

   protected abstract ContainerSerializer _withValueTypeSerializer(TypeSerializer var1);

   /** @deprecated */
   @Deprecated
   protected boolean hasContentTypeAnnotation(SerializerProvider var1, BeanProperty var2) {
      return false;
   }
}
