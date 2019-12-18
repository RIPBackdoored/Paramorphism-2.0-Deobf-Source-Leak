package paramorphism-obfuscator;

import java.nio.file.Path;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class ti extends Lambda implements Function1 {
   public static final ti bia = new ti();

   public Object invoke(Object var1) {
      return this.a((Path)var1);
   }

   @NotNull
   public final String a(Path var1) {
      return StringsKt.drop(var1.toString(), 1);
   }

   public ti() {
      super(1);
   }
}
