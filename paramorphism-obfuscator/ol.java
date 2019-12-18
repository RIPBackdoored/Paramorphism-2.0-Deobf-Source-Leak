package paramorphism-obfuscator;

import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class ol extends Lambda implements Function1 {
   public final String zj;
   public final String zk;
   public final Set zl;

   public Object invoke(Object var1) {
      this.a((le)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull le var1) {
      le[] var4 = var1.e();
      int var5 = var4.length;

      le var2;
      for(int var3 = 0; var3 < var5; ++var3) {
         var2 = var4[var3];
         if (ArraysKt.contains(var2.b(), this.zj + ' ' + this.zk)) {
            this.zl.add(var2);
         }

         ((ol)this).a(var2);
      }

      le var10000 = var1.d();
      if (var10000 != null) {
         var2 = var10000;
         boolean var7 = false;
         boolean var8 = false;
         boolean var6 = false;
         if (ArraysKt.contains(var2.b(), this.zj + ' ' + this.zk)) {
            this.zl.add(var2);
         }

         this.a(var2);
      }

   }

   public ol(String var1, String var2, Set var3) {
      super(1);
      this.zj = var1;
      this.zk = var2;
      this.zl = var3;
   }
}
