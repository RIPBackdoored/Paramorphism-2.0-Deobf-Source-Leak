package paramorphism-obfuscator;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.Resource;

public final class tb extends Lambda implements Function1 {
   public final sy bht;

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   @NotNull
   public final ClassInfo a(@NotNull Pair var1) {
      String var2 = (String)var1.component1();
      Resource var3 = (Resource)var1.component2();
      return sy.a(this.bht, StringsKt.dropLast(var2, ".class".length()), var3);
   }

   public tb(sy var1) {
      super(1);
      this.bht = var1;
   }
}
