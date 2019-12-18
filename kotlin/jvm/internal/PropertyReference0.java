package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty$Getter;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty0$Getter;

public abstract class PropertyReference0 extends PropertyReference implements KProperty0 {
   public PropertyReference0() {
      super();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public PropertyReference0(Object var1) {
      super(var1);
   }

   protected KCallable computeReflected() {
      return Reflection.property0(this);
   }

   public Object invoke() {
      return this.get();
   }

   public KProperty0$Getter getGetter() {
      return ((KProperty0)this.getReflected()).getGetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate() {
      return ((KProperty0)this.getReflected()).getDelegate();
   }

   public KProperty$Getter getGetter() {
      return this.getGetter();
   }
}
