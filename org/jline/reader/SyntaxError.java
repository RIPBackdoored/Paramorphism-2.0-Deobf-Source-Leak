package org.jline.reader;

public class SyntaxError extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private final int line;
   private final int column;

   public SyntaxError(int var1, int var2, String var3) {
      super(var3);
      this.line = var1;
      this.column = var2;
   }

   public int column() {
      return this.column;
   }

   public int line() {
      return this.line;
   }
}
