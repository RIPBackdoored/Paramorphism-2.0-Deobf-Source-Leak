package com.fasterxml.jackson.dataformat.yaml.snakeyaml.error;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.dataformat.yaml.JacksonYAMLParseException;

/** @deprecated */
@Deprecated
public class YAMLException extends JacksonYAMLParseException {
   private static final long serialVersionUID = 1L;

   public YAMLException(JsonParser var1, org.yaml.snakeyaml.error.YAMLException var2) {
      super(var1, var2.getMessage(), var2);
   }

   public static YAMLException from(JsonParser var0, org.yaml.snakeyaml.error.YAMLException var1) {
      return new YAMLException(var0, var1);
   }
}
