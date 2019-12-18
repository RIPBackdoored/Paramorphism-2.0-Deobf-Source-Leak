package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.InsnList;

public final class rq extends Lambda implements Function0 {
   public final rn bfr;
   public final rp bfs;

   public rq(rn var1, rp var2) {
      super(0);
      this.bfr = var1;
      this.bfs = var2;
   }

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final InsnList a() {
      return (InsnList)xg.a((Function1)(new rh(this))).getFirst();
   }
}
