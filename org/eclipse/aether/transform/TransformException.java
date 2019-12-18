package org.eclipse.aether.transform;

public class TransformException extends Exception {
   public TransformException() {
      super("Transformation failed");
   }

   public TransformException(String var1) {
      super(var1);
   }

   public TransformException(Throwable var1) {
      super(var1);
   }

   public TransformException(String var1, Throwable var2) {
      super(var1, var2);
   }
}
