package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;

public final class mo {
   private static final boolean a(int var0) {
      return (var0 & 1) == 0 && (var0 & 4) == 0 && (var0 & 2) == 0;
   }

   public static final void a(@NotNull kg var0) {
      var0.a().a(Reflection.getOrCreateKotlinClass(md.class), (Function1)mn.vd);
   }

   public static final boolean b(int var0) {
      return a(var0);
   }
}
