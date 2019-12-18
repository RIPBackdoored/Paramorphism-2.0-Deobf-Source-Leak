package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.BufferResource;
import site.hackery.paramorphism.api.resources.Resource;

public final class nf extends Lambda implements Function1 {
   public final mz wh;
   public final Function1 wi;
   public final nd wj;

   public Object invoke(Object var1) {
      this.a((me)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull me var1) {
      if ((Boolean)this.wi.invoke(var1.a().getOriginalName())) {
         byte[] var2 = this.wj.a(var1.c().getData());
         int var3 = var1.c().getOrder();
         var1.a(this.wh.b().getPackedPrefix() + StringsKt.removeSuffix(var1.b(), (CharSequence)".class") + this.wh.b().getPackedExtension());
         var1.a((Resource)(new BufferResource(var2, var3)));
      }
   }

   public nf(mz var1, Function1 var2, nd var3) {
      super(1);
      this.wh = var1;
      this.wi = var2;
      this.wj = var3;
   }
}
