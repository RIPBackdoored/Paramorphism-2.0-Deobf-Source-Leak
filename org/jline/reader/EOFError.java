package org.jline.reader;

public class EOFError extends SyntaxError {
   private static final long serialVersionUID = 1L;
   private final String missing;

   public EOFError(int var1, int var2, String var3) {
      this(var1, var2, var3, (String)null);
   }

   public EOFError(int var1, int var2, String var3, String var4) {
      super(var1, var2, var3);
      this.missing = var4;
   }

   public String getMissing() {
      return this.missing;
   }
}
