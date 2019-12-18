package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.exc.InvalidNullException;
import com.fasterxml.jackson.databind.util.AccessPattern;
import java.io.Serializable;

public class NullsFailProvider implements NullValueProvider, Serializable {
   private static final long serialVersionUID = 1L;
   protected final PropertyName _name;
   protected final JavaType _type;

   protected NullsFailProvider(PropertyName var1, JavaType var2) {
      super();
      this._name = var1;
      this._type = var2;
   }

   public static NullsFailProvider constructForProperty(BeanProperty var0) {
      return new NullsFailProvider(var0.getFullName(), var0.getType());
   }

   public static NullsFailProvider constructForRootValue(JavaType var0) {
      return new NullsFailProvider((PropertyName)null, var0);
   }

   public AccessPattern getNullAccessPattern() {
      return AccessPattern.DYNAMIC;
   }

   public Object getNullValue(DeserializationContext var1) throws JsonMappingException {
      throw InvalidNullException.from(var1, this._name, this._type);
   }
}
