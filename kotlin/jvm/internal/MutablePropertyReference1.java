package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty$Setter;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty1$Setter;
import kotlin.reflect.KProperty$Getter;
import kotlin.reflect.KProperty1$Getter;

public abstract class MutablePropertyReference1 extends MutablePropertyReference implements KMutableProperty1 {
   public MutablePropertyReference1() {
      super();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public MutablePropertyReference1(Object var1) {
      super(var1);
   }

   protected KCallable computeReflected() {
      return Reflection.mutableProperty1(this);
   }

   public Object invoke(Object var1) {
      return this.get(var1);
   }

   public KProperty1$Getter getGetter() {
      return ((KMutableProperty1)this.getReflected()).getGetter();
   }

   public KMutableProperty1$Setter getSetter() {
      return ((KMutableProperty1)this.getReflected()).getSetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate(Object var1) {
      return ((KMutableProperty1)this.getReflected()).getDelegate(var1);
   }

   public KMutableProperty$Setter getSetter() {
      return this.getSetter();
   }

   public KProperty$Getter getGetter() {
      return this.getGetter();
   }
}
