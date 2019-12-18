package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty$Setter;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KMutableProperty2$Setter;
import kotlin.reflect.KProperty$Getter;
import kotlin.reflect.KProperty2$Getter;

public abstract class MutablePropertyReference2 extends MutablePropertyReference implements KMutableProperty2 {
   public MutablePropertyReference2() {
      super();
   }

   protected KCallable computeReflected() {
      return Reflection.mutableProperty2(this);
   }

   public Object invoke(Object var1, Object var2) {
      return this.get(var1, var2);
   }

   public KProperty2$Getter getGetter() {
      return ((KMutableProperty2)this.getReflected()).getGetter();
   }

   public KMutableProperty2$Setter getSetter() {
      return ((KMutableProperty2)this.getReflected()).getSetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate(Object var1, Object var2) {
      return ((KMutableProperty2)this.getReflected()).getDelegate(var1, var2);
   }

   public KMutableProperty$Setter getSetter() {
      return this.getSetter();
   }

   public KProperty$Getter getGetter() {
      return this.getGetter();
   }
}
