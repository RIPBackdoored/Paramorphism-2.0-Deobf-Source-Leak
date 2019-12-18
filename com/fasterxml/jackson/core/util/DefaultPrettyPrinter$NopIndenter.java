package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.io.Serializable;

public class DefaultPrettyPrinter$NopIndenter implements DefaultPrettyPrinter$Indenter, Serializable {
   public static final DefaultPrettyPrinter$NopIndenter instance = new DefaultPrettyPrinter$NopIndenter();

   public DefaultPrettyPrinter$NopIndenter() {
      super();
   }

   public void writeIndentation(JsonGenerator var1, int var2) throws IOException {
   }

   public boolean isInline() {
      return true;
   }
}
