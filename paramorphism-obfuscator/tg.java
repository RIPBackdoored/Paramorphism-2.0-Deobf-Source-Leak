package paramorphism-obfuscator;

import java.io.InputStream;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class tg extends Lambda implements Function1 {
   public static final tg bhy = new tg();

   public Object invoke(Object var1) {
      return this.a((Pair)var1);
   }

   public final boolean a(@NotNull Pair var1) {
      InputStream var2 = (InputStream)var1.component2();
      return var2.available() > 0;
   }

   public tg() {
      super(1);
   }
}
