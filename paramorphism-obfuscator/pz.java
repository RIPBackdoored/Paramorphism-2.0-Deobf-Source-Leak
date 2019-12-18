package paramorphism-obfuscator;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;

public final class pz extends Lambda implements Function0 {
   public final ps bcf;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final qm a() {
      Ref$IntRef var10000 = this.bcf.bbn;
      int var2;
      var10000.element = (var2 = var10000.element) + 1;
      boolean var8;
      if (var2 > 16313) {
         this.bcf.bbn.element = 0;
         var8 = true;
      } else {
         var8 = this.bcf.bbo.isEmpty();
      }

      boolean var1 = var8;
      qm var9;
      if (var1) {
         qm var7 = new qm(qc.b(this.bcf.bbm) + this.bcf.bbp.a(-1, this.bcf.bbo.size(), oj.zf));
         boolean var3 = false;
         boolean var4 = false;
         boolean var6 = false;
         this.bcf.bbo.add(var7);
         var9 = var7;
      } else {
         var9 = (qm)CollectionsKt.last(this.bcf.bbo);
      }

      return var9;
   }

   public pz(ps var1) {
      super(0);
      this.bcf = var1;
   }
}
