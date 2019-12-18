package org.jline.reader;

public interface Parser {
   ParsedLine parse(String var1, int var2, Parser$ParseContext var3) throws SyntaxError;

   default ParsedLine parse(String var1, int var2) throws SyntaxError {
      return this.parse(var1, var2, Parser$ParseContext.UNSPECIFIED);
   }

   default boolean isEscapeChar(char var1) {
      return var1 == '\\';
   }
}
