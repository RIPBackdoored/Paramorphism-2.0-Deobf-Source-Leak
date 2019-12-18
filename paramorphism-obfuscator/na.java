package paramorphism-obfuscator;

import java.util.Collection;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class na extends Lambda implements Function1 {
   public final Set wb;
   public final kg wc;

   public Object invoke(Object var1) {
      this.a((lx)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull lx var1) {
      CollectionsKt.addAll((Collection)this.wb, this.wc.c().names());
   }

   public na(Set var1, kg var2) {
      super(1);
      this.wb = var1;
      this.wc = var2;
   }
}
