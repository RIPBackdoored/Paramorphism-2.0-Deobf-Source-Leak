package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.annotations.NotNull;

public final class ri extends FunctionReference implements Function1 {
   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      rn.a((rn)this.receiver, var1);
   }

   public final KDeclarationContainer getOwner() {
      return Reflection.getOrCreateKotlinClass(rn.class);
   }

   public final String getName() {
      return "convertFieldValueConstantsToInitializerAssignments";
   }

   public final String getSignature() {
      return "convertFieldValueConstantsToInitializerAssignments(Lsite/hackery/paramorphism/events/VisitClassEvent;)V";
   }

   public ri(rn var1) {
      super(1, var1);
   }
}
