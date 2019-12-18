package paramorphism-obfuscator.verifierDisabler;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class FirstLoadedClass extends Lambda implements Function1 {
   public static final FirstLoadedClass bhh = new FirstLoadedClass();

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      ClassWrapper.addField(var1, var1.e().a((AccessWrapper)var1.h()), var1.bi(), "/*", (String)null, 69420, 8, (Object)null);
      ClassWrapper.addField(var1, var1.e().a((AccessWrapper)var1.h()), Reflection.getOrCreateKotlinClass(Object.class), "*/", (String)null, (Object)null, 24, (Object)null);
   }

   public FirstLoadedClass() {
      super(1);
   }
}
