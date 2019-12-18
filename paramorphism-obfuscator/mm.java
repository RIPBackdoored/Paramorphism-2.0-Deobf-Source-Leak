package paramorphism-obfuscator;

import java.util.jar.Attributes.Name;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.StrategyConfiguration;
import site.hackery.paramorphism.api.config.StrategyIrregularity;

public abstract class mm {
   private final mq vc;

   @NotNull
   protected abstract StrategyConfiguration c();

   protected abstract void f(@NotNull kg var1);

   public final void b(@NotNull kg var1) {
      Boolean var10000 = this.c().getEnabled();
      if (var10000 != null ? var10000 : this.c(var1)) {
         this.f(var1);
      }

   }

   private final boolean c(kg var1) {
      StrategyIrregularity[] var2 = this.c().getIrregularities();
      if (ArraysKt.contains(var2, kd.qw)) {
         return false;
      } else if (ArraysKt.contains(var2, kc.qv)) {
         Object var10000 = var1.g().getMainAttributes().get(Name.MAIN_CLASS);
         if (!(var10000 instanceof String)) {
            var10000 = null;
         }

         return (String)var10000 != null;
      } else {
         return true;
      }
   }

   protected final boolean a(@NotNull kg var1, @NotNull String var2) {
      mm var3 = (mm)this;
      return var3.c().getOverrideGlobalMask() ? li.a(var3.c().getMask(), var2) : li.a(var1.i().getMask(), var2) && li.a(var3.c().getMask(), var2);
   }

   @NotNull
   protected final Function1 d(@NotNull kg var1) {
      return (Function1)(new mr(this, var1));
   }

   @NotNull
   protected final Function1 e(@NotNull kg var1) {
      return (Function1)(new mt(this, var1));
   }

   protected final void a(@NotNull kg var1, @NotNull KClass var2, @NotNull Function1 var3) {
      var1.a().a(var2, (ig)(new mp(this, var3)));
   }

   protected final void a(@NotNull kg var1, @NotNull KClass var2, @NotNull Function1 var3, @NotNull Function1 var4) {
      var1.a().a(var2, (ig)(new ms(this, var3, var4)));
   }

   public mm(@NotNull mq var1) {
      super();
      this.vc = var1;
   }

   public mm(mq var1, int var2, DefaultConstructorMarker var3) {
      if ((var2 & 1) != 0) {
         var1 = mq.vh;
      }

      this(var1);
   }

   public mm() {
      this((mq)null, 1, (DefaultConstructorMarker)null);
   }

   public static final mq a(mm var0) {
      return var0.vc;
   }
}
