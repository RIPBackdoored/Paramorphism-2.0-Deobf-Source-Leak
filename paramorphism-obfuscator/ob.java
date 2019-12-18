package paramorphism-obfuscator;

import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.commons.Remapper;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class ob extends Lambda implements Function1 {
   public final ow xr;
   public final kg xs;
   public final lu xt;
   public final ClassSet xu;

   public Object invoke(Object var1) {
      this.a((lx)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull lx var1) {
      lc var2 = new lc(this.xs.b(), this.xs.f());
      oh var3 = new oh();
      if (this.xr.a().getFlat()) {
         op.b(var3, this.xs, this.xr.a());
      } else {
         op.a(var3, this.xs, this.xr.a());
      }

      op.a(var3, this.xt, var2, this.xs, this.xr.a());
      op.c(var3, this.xs, this.xr.a());
      Map var4 = pg.a(var3, this.xu, var2);
      ow.a(this.xr, new la((Remapper)(new pb((Remapper)(new ot(this.xs, var3, this.xt, ow.a(this.xr, this.xs), var2, var4, this.xr.a().getFlat()))))));
   }

   public ob(ow var1, kg var2, lu var3, ClassSet var4) {
      super(1);
      this.xr = var1;
      this.xs = var2;
      this.xt = var3;
      this.xu = var4;
   }
}
