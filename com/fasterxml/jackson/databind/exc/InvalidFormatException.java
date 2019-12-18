package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;

public class InvalidFormatException extends MismatchedInputException {
   private static final long serialVersionUID = 1L;
   protected final Object _value;

   /** @deprecated */
   @Deprecated
   public InvalidFormatException(String var1, Object var2, Class var3) {
      super((JsonParser)null, var1);
      this._value = var2;
      this._targetType = var3;
   }

   /** @deprecated */
   @Deprecated
   public InvalidFormatException(String var1, JsonLocation var2, Object var3, Class var4) {
      super((JsonParser)null, var1, (JsonLocation)var2);
      this._value = var3;
      this._targetType = var4;
   }

   public InvalidFormatException(JsonParser var1, String var2, Object var3, Class var4) {
      super(var1, var2, var4);
      this._value = var3;
   }

   public static InvalidFormatException from(JsonParser var0, String var1, Object var2, Class var3) {
      return new InvalidFormatException(var0, var1, var2, var3);
   }

   public Object getValue() {
      return this._value;
   }
}
