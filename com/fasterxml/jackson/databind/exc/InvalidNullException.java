package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.util.ClassUtil;

public class InvalidNullException extends MismatchedInputException {
   private static final long serialVersionUID = 1L;
   protected final PropertyName _propertyName;

   protected InvalidNullException(DeserializationContext var1, String var2, PropertyName var3) {
      super(var1.getParser(), var2);
      this._propertyName = var3;
   }

   public static InvalidNullException from(DeserializationContext var0, PropertyName var1, JavaType var2) {
      String var3 = String.format("Invalid `null` value encountered for property %s", ClassUtil.quotedOr(var1, "<UNKNOWN>"));
      InvalidNullException var4 = new InvalidNullException(var0, var3, var1);
      if (var2 != null) {
         var4.setTargetType(var2);
      }

      return var4;
   }

   public PropertyName getPropertyName() {
      return this._propertyName;
   }
}
