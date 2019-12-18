package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KMutableProperty;

public abstract class MutablePropertyReference extends PropertyReference implements KMutableProperty {
   public MutablePropertyReference() {
      super();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public MutablePropertyReference(Object var1) {
      super(var1);
   }
}
