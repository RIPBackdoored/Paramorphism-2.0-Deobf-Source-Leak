package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty$Setter;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty0$Setter;
import kotlin.reflect.KProperty$Getter;
import kotlin.reflect.KProperty0$Getter;

public abstract class MutablePropertyReference0 extends MutablePropertyReference implements KMutableProperty0 {
   public MutablePropertyReference0() {
      super();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public MutablePropertyReference0(Object var1) {
      super(var1);
   }

   protected KCallable computeReflected() {
      return Reflection.mutableProperty0(this);
   }

   public Object invoke() {
      return this.get();
   }

   public KProperty0$Getter getGetter() {
      return ((KMutableProperty0)this.getReflected()).getGetter();
   }

   public KMutableProperty0$Setter getSetter() {
      return ((KMutableProperty0)this.getReflected()).getSetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate() {
      return ((KMutableProperty0)this.getReflected()).getDelegate();
   }

   public KMutableProperty$Setter getSetter() {
      return this.getSetter();
   }

   public KProperty$Getter getGetter() {
      return this.getGetter();
   }
}
