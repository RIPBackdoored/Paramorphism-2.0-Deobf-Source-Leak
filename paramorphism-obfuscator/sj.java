package paramorphism-obfuscator;

import java.util.Set;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

public final class sj extends Lambda implements Function1 {
   public final Lazy bgz;
   public final KProperty bha;

   public Object invoke(Object var1) {
      this.a((mf)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull mf var1) {
      Lazy var2 = this.bgz;
      String var3 = null;
      KProperty var4 = this.bha;
      boolean var5 = false;
      if (((Set)var2.getValue()).contains(var1.a())) {
         char var8 = 0;
         var3 = var1.b();
         boolean var9 = false;
         String var7 = var8 + var3;
         var1.a(var7);
      }

   }

   public sj(Lazy var1, KProperty var2) {
      super(1);
      this.bgz = var1;
      this.bha = var2;
   }
}
