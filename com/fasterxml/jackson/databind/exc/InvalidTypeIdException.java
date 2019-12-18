package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;

public class InvalidTypeIdException extends MismatchedInputException {
   private static final long serialVersionUID = 1L;
   protected final JavaType _baseType;
   protected final String _typeId;

   public InvalidTypeIdException(JsonParser var1, String var2, JavaType var3, String var4) {
      super(var1, var2);
      this._baseType = var3;
      this._typeId = var4;
   }

   public static InvalidTypeIdException from(JsonParser var0, String var1, JavaType var2, String var3) {
      return new InvalidTypeIdException(var0, var1, var2, var3);
   }

   public JavaType getBaseType() {
      return this._baseType;
   }

   public String getTypeId() {
      return this._typeId;
   }
}
