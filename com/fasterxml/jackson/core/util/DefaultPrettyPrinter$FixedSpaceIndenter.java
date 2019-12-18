package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;

public class DefaultPrettyPrinter$FixedSpaceIndenter extends DefaultPrettyPrinter$NopIndenter {
   public static final DefaultPrettyPrinter$FixedSpaceIndenter instance = new DefaultPrettyPrinter$FixedSpaceIndenter();

   public DefaultPrettyPrinter$FixedSpaceIndenter() {
      super();
   }

   public void writeIndentation(JsonGenerator var1, int var2) throws IOException {
      var1.writeRaw(' ');
   }

   public boolean isInline() {
      return true;
   }
}
