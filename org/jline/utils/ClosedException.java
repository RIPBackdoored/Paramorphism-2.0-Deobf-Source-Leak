package org.jline.utils;

import java.io.IOException;

public class ClosedException extends IOException {
   private static final long serialVersionUID = 3085420657077696L;

   public ClosedException() {
      super();
   }

   public ClosedException(String var1) {
      super(var1);
   }

   public ClosedException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public ClosedException(Throwable var1) {
      super(var1);
   }
}
