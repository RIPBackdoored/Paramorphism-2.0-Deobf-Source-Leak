package org.eclipse.aether;

public class RequestTrace {
   private final RequestTrace parent;
   private final Object data;

   public static RequestTrace newChild(RequestTrace var0, Object var1) {
      return var0 == null ? new RequestTrace(var1) : var0.newChild(var1);
   }

   public RequestTrace(Object var1) {
      this((RequestTrace)null, var1);
   }

   protected RequestTrace(RequestTrace var1, Object var2) {
      super();
      this.parent = var1;
      this.data = var2;
   }

   public final Object getData() {
      return this.data;
   }

   public final RequestTrace getParent() {
      return this.parent;
   }

   public RequestTrace newChild(Object var1) {
      return new RequestTrace(this, var1);
   }

   public String toString() {
      return String.valueOf(this.getData());
   }
}
