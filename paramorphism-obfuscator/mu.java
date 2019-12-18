package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.annotations.NotNull;

public final class mu extends FunctionReference implements Function1 {
   public final nf vs;

   public Object invoke(Object var1) {
      this.a((me)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull me var1) {
      this.vs.a(var1);
   }

   public final KDeclarationContainer getOwner() {
      return null;
   }

   public final String getName() {
      return "packAssembledClass";
   }

   public final String getSignature() {
      return "invoke(Lsite/hackery/paramorphism/events/AssembleClassEvent;)V";
   }

   public mu(nf var1) {
      super(1);
      this.vs = var1;
   }
}
