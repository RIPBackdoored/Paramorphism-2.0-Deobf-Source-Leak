package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class re extends Lambda implements Function1 {
   public final rn ber;
   public final List bes;
   public final lu bet;

   public Object invoke(Object var1) {
      this.a((ClassInfoList)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassInfoList var1) {
      var1.addClassNode(ro.a(rn.c(this.ber).getInternalName(), this.bes, this.bet));
      Iterator var3 = this.bes.iterator();

      while(var3.hasNext()) {
         ra var2 = (ra)var3.next();
         var1.addClassNode(qz.a(var2, this.bet));
      }

   }

   public re(rn var1, List var2, lu var3) {
      super(1);
      this.ber = var1;
      this.bes = var2;
      this.bet = var3;
   }
}
