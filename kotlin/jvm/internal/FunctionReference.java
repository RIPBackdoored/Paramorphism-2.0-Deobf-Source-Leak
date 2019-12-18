package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference extends CallableReference implements FunctionBase, KFunction {
   private final int arity;

   public FunctionReference(int var1) {
      super();
      this.arity = var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public FunctionReference(int var1, Object var2) {
      super(var2);
      this.arity = var1;
   }

   public int getArity() {
      return this.arity;
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KFunction getReflected() {
      return (KFunction)super.getReflected();
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KCallable computeReflected() {
      return Reflection.function(this);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isInline() {
      return this.getReflected().isInline();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isExternal() {
      return this.getReflected().isExternal();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isOperator() {
      return this.getReflected().isOperator();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isInfix() {
      return this.getReflected().isInfix();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof FunctionReference)) {
         return var1 instanceof KFunction ? var1.equals(this.compute()) : false;
      } else {
         boolean var10000;
         label31: {
            FunctionReference var2 = (FunctionReference)var1;
            if (this.getOwner() == null) {
               if (var2.getOwner() != null) {
                  break label31;
               }
            } else if (!this.getOwner().equals(var2.getOwner())) {
               break label31;
            }

            if (this.getName().equals(var2.getName()) && this.getSignature().equals(var2.getSignature()) && Intrinsics.areEqual(this.getBoundReceiver(), var2.getBoundReceiver())) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      }
   }

   public int hashCode() {
      return ((this.getOwner() == null ? 0 : this.getOwner().hashCode() * 31) + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
   }

   public String toString() {
      KCallable var1 = this.compute();
      if (var1 != this) {
         return var1.toString();
      } else {
         return "<init>".equals(this.getName()) ? "constructor (Kotlin reflection is not available)" : "function " + this.getName() + " (Kotlin reflection is not available)";
      }
   }

   protected KCallable getReflected() {
      return this.getReflected();
   }
}
