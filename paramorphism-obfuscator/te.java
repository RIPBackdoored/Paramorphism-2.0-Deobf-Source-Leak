package paramorphism-obfuscator;

import java.nio.file.Path;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.LazyResource;

public final class te extends Lambda implements Function1 {
   public static final te bhw = new te();

   public Object invoke(Object var1) {
      return this.a((Path)var1);
   }

   @NotNull
   public final Pair a(Path var1) {
      return new Pair(StringsKt.drop(var1.toString(), 1), new LazyResource((Function0)(new tj(var1)), 0, 2, (DefaultConstructorMarker)null));
   }

   public te() {
      super(1);
   }
}
