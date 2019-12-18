package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;

public abstract class PropertyReference extends CallableReference implements KProperty {
   public PropertyReference() {
      super();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public PropertyReference(Object var1) {
      super(var1);
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KProperty getReflected() {
      return (KProperty)super.getReflected();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isLateinit() {
      return this.getReflected().isLateinit();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isConst() {
      return this.getReflected().isConst();
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof PropertyReference)) {
         return var1 instanceof KProperty ? var1.equals(this.compute()) : false;
      } else {
         PropertyReference var2 = (PropertyReference)var1;
         return this.getOwner().equals(var2.getOwner()) && this.getName().equals(var2.getName()) && this.getSignature().equals(var2.getSignature()) && Intrinsics.areEqual(this.getBoundReceiver(), var2.getBoundReceiver());
      }
   }

   public int hashCode() {
      return (this.getOwner().hashCode() * 31 + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
   }

   public String toString() {
      KCallable var1 = this.compute();
      return var1 != this ? var1.toString() : "property " + this.getName() + " (Kotlin reflection is not available)";
   }

   protected KCallable getReflected() {
      return this.getReflected();
   }
}
