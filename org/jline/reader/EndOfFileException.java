package org.jline.reader;

public class EndOfFileException extends RuntimeException {
   private static final long serialVersionUID = 528485360925144689L;

   public EndOfFileException() {
      super();
   }

   public EndOfFileException(String var1) {
      super(var1);
   }

   public EndOfFileException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public EndOfFileException(Throwable var1) {
      super(var1);
   }

   public EndOfFileException(String var1, Throwable var2, boolean var3, boolean var4) {
      super(var1, var2, var3, var4);
   }
}
