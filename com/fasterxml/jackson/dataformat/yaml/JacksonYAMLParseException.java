package com.fasterxml.jackson.dataformat.yaml;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

public class JacksonYAMLParseException extends JsonParseException {
   private static final long serialVersionUID = 1L;

   public JacksonYAMLParseException(JsonParser var1, String var2, Exception var3) {
      super((JsonParser)var1, (String)var2, (Throwable)var3);
   }
}
