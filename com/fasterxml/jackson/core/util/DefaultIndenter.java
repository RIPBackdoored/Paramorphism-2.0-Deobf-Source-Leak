package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;

public class DefaultIndenter extends DefaultPrettyPrinter$NopIndenter {
   private static final long serialVersionUID = 1L;
   public static final String SYS_LF;
   public static final DefaultIndenter SYSTEM_LINEFEED_INSTANCE;
   private static final int INDENT_LEVELS = 16;
   private final char[] indents;
   private final int charsPerLevel;
   private final String eol;

   public DefaultIndenter() {
      this("  ", SYS_LF);
   }

   public DefaultIndenter(String var1, String var2) {
      super();
      this.charsPerLevel = var1.length();
      this.indents = new char[var1.length() * 16];
      int var3 = 0;

      for(int var4 = 0; var4 < 16; ++var4) {
         var1.getChars(0, var1.length(), this.indents, var3);
         var3 += var1.length();
      }

      this.eol = var2;
   }

   public DefaultIndenter withLinefeed(String var1) {
      return var1.equals(this.eol) ? this : new DefaultIndenter(this.getIndent(), var1);
   }

   public DefaultIndenter withIndent(String var1) {
      return var1.equals(this.getIndent()) ? this : new DefaultIndenter(var1, this.eol);
   }

   public boolean isInline() {
      return false;
   }

   public void writeIndentation(JsonGenerator var1, int var2) throws IOException {
      var1.writeRaw(this.eol);
      if (var2 > 0) {
         for(var2 *= this.charsPerLevel; var2 > this.indents.length; var2 -= this.indents.length) {
            var1.writeRaw((char[])this.indents, 0, this.indents.length);
         }

         var1.writeRaw((char[])this.indents, 0, var2);
      }

   }

   public String getEol() {
      return this.eol;
   }

   public String getIndent() {
      return new String(this.indents, 0, this.charsPerLevel);
   }

   static {
      String var0;
      try {
         var0 = System.getProperty("line.separator");
      } catch (Throwable var2) {
         var0 = "\n";
      }

      SYS_LF = var0;
      SYSTEM_LINEFEED_INSTANCE = new DefaultIndenter("  ", SYS_LF);
   }
}
