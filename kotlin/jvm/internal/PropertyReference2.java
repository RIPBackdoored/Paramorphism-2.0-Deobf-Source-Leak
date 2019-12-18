package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty$Getter;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KProperty2$Getter;

public abstract class PropertyReference2 extends PropertyReference implements KProperty2 {
   public PropertyReference2() {
      super();
   }

   protected KCallable computeReflected() {
      return Reflection.property2(this);
   }

   public Object invoke(Object var1, Object var2) {
      return this.get(var1, var2);
   }

   public KProperty2$Getter getGetter() {
      return ((KProperty2)this.getReflected()).getGetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate(Object var1, Object var2) {
      return ((KProperty2)this.getReflected()).getDelegate(var1, var2);
   }

   public KProperty$Getter getGetter() {
      return this.getGetter();
   }
}
