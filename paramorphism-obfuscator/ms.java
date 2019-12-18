package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

public final class ms implements ig {
   public final mm vn;
   public final Function1 vo;
   public final Function1 vp;

   public void b(@NotNull Object var1) {
      if (!(var1 instanceof mb) || ((mb)var1).a() == mm.a(this.vn)) {
         if ((Boolean)this.vo.invoke(var1)) {
            this.vp.invoke(var1);
         }

      }
   }

   public ms(mm var1, Function1 var2, Function1 var3) {
      super();
      this.vn = var1;
      this.vo = var2;
      this.vp = var3;
   }
}
