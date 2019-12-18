package paramorphism-obfuscator;

import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class pj extends Lambda implements Function1 {
   public static final pj bbb = new pj();

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      if (var1.d().visibleAnnotations != null && var1.d().visibleAnnotations.removeIf((Predicate)pl.bbd)) {
         var1.a(true);
      }

   }

   public pj() {
      super(1);
   }
}
