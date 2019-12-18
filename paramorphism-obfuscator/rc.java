package paramorphism-obfuscator;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;

public final class rc extends Lambda implements Function0 {
   public final rj bej;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final ra a() {
      Ref$IntRef var10000 = this.bej.bey;
      int var2;
      var10000.element = (var2 = var10000.element) + 1;
      boolean var8;
      if (var2 > 128) {
         this.bej.bey.element = 0;
         var8 = true;
      } else {
         var8 = this.bej.bez.isEmpty();
      }

      boolean var1 = var8;
      ra var9;
      if (var1) {
         ra var7 = new ra(rn.b(this.bej.bex) + this.bej.bfa.a(-1, this.bej.bez.size(), oj.zf));
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         this.bej.bez.add(var7);
         var9 = var7;
      } else {
         var9 = (ra)CollectionsKt.last(this.bej.bez);
      }

      return var9;
   }

   public rc(rj var1) {
      super(0);
      this.bej = var1;
   }
}
