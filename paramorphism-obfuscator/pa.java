package paramorphism-obfuscator;

import java.util.Set;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

public final class pa extends Lambda implements Function1 {
   public final Lazy bao;
   public final KProperty bap;
   public final String baq;

   public Object invoke(Object var1) {
      this.a((mf)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull mf var1) {
      Lazy var2 = this.bao;
      Object var3 = null;
      KProperty var4 = this.bap;
      boolean var5 = false;
      if (((Set)var2.getValue()).contains(var1.a())) {
         var1.a(this.baq + var1.b());
      }

   }

   public pa(Lazy var1, KProperty var2, String var3) {
      super(1);
      this.bao = var1;
      this.bap = var2;
      this.baq = var3;
   }
}
