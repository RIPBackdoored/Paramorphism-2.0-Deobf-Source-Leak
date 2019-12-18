package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import java.io.Serializable;
import java.util.HashMap;

public class SimpleKeyDeserializers implements KeyDeserializers, Serializable {
   private static final long serialVersionUID = 1L;
   protected HashMap _classMappings = null;

   public SimpleKeyDeserializers() {
      super();
   }

   public SimpleKeyDeserializers addDeserializer(Class var1, KeyDeserializer var2) {
      if (this._classMappings == null) {
         this._classMappings = new HashMap();
      }

      this._classMappings.put(new ClassKey(var1), var2);
      return this;
   }

   public KeyDeserializer findKeyDeserializer(JavaType var1, DeserializationConfig var2, BeanDescription var3) {
      return this._classMappings == null ? null : (KeyDeserializer)this._classMappings.get(new ClassKey(var1.getRawClass()));
   }
}
