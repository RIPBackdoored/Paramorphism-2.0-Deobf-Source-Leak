package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.annotations.NotNull;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qy extends FunctionReference implements Function1 {
   public static final qy bef = new qy();

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper var1) {
      qz.b(var1);
   }

   public final KDeclarationContainer getOwner() {
      return Reflection.getOrCreateKotlinPackage(qz.class, "paramorphism");
   }

   public final String getName() {
      return "calculateCallerCode";
   }

   public final String getSignature() {
      return "calculateCallerCode(Lcodes/som/anthony/koffee/MethodAssembly;)V";
   }

   public qy() {
      super(1);
   }
}
