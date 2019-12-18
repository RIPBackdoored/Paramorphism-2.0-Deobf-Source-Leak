package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JsonEOFException extends JsonParseException {
   private static final long serialVersionUID = 1L;
   protected final JsonToken _token;

   public JsonEOFException(JsonParser var1, JsonToken var2, String var3) {
      super(var1, var3);
      this._token = var2;
   }

   public JsonToken getTokenBeingDecoded() {
      return this._token;
   }
}
