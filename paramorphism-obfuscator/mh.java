package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class mh extends Lambda implements Function0 {
   public final kg ux;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final ResourceSet a() {
      tc var1 = new tc(this.ux.i().getInput());
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      Function1 var6 = (Function1)sr.bhj;
      ElementMask var10000 = this.ux.i().getShadedLibraries();
      ResourceSet var12;
      if (var10000 != null) {
         ElementMask var7 = var10000;
         boolean var8 = false;
         boolean var9 = false;
         boolean var11 = false;
         var12 = (ResourceSet)(new tr(var7, (ResourceSet)var1, var6));
      } else {
         var12 = (ResourceSet)(new sv());
      }

      return var12;
   }

   public mh(kg var1) {
      super(0);
      this.ux = var1;
   }
}
