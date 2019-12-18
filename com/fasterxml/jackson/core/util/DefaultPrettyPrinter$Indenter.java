package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;

public interface DefaultPrettyPrinter$Indenter {
   void writeIndentation(JsonGenerator var1, int var2) throws IOException;

   boolean isInline();
}
