package paramorphism-obfuscator;

import java.util.Map;
import java.util.jar.Attributes.Name;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class ReplaceMainClassLambda extends Lambda implements Function1 {
   public final kg bgu;

   public Object invoke(Object var1) {
      this.a((mg)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull mg var1) {
      ((Map)this.bgu.g().getMainAttributes()).put(Name.MAIN_CLASS, StringsKt.replace$default("dev/paramorphism/runtime/Main", '/', '.', false, 4, (Object)null));
   }

   public ReplaceMainClassLambda(kg var1) {
      super(1);
      this.bgu = var1;
   }
}
