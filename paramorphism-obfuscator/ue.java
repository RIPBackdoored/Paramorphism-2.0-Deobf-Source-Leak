package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.resources.ClassSet;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class ue extends Lambda implements Function0 {
   public final kg bjd;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final ub a() {
      sy var1 = new sy((ResourceSet)(new tc(this.bjd.i().getInput())), 0, 2, (DefaultConstructorMarker)null);
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      ElementMask var10000 = this.bjd.i().getShadedLibraries();
      ClassSet var15;
      if (var10000 != null) {
         ElementMask var6 = var10000;
         boolean var7 = false;
         boolean var8 = false;
         boolean var10 = false;
         var15 = (ClassSet)(new ts(var6, (ClassSet)var1));
      } else {
         var15 = (ClassSet)var1;
      }

      ClassSet var13 = var15;
      return new ub(var13);
   }

   public ue(kg var1) {
      super(0);
      this.bjd = var1;
   }
}
