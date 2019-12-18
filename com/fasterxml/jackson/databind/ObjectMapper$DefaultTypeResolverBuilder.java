package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import java.io.Serializable;
import java.util.Collection;

public class ObjectMapper$DefaultTypeResolverBuilder extends StdTypeResolverBuilder implements Serializable {
   private static final long serialVersionUID = 1L;
   protected final ObjectMapper$DefaultTyping _appliesFor;

   public ObjectMapper$DefaultTypeResolverBuilder(ObjectMapper$DefaultTyping var1) {
      super();
      this._appliesFor = var1;
   }

   public TypeDeserializer buildTypeDeserializer(DeserializationConfig var1, JavaType var2, Collection var3) {
      return this.useForType(var2) ? super.buildTypeDeserializer(var1, var2, var3) : null;
   }

   public TypeSerializer buildTypeSerializer(SerializationConfig var1, JavaType var2, Collection var3) {
      return this.useForType(var2) ? super.buildTypeSerializer(var1, var2, var3) : null;
   }

   public boolean useForType(JavaType var1) {
      // $FF: Couldn't be decompiled
   }
}
