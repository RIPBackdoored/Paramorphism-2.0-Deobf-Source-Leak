package paramorphism-obfuscator;

import java.util.Collection;
import java.util.Stack;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;

public final class os extends Lambda implements Function0 {
   public final Stack zv;
   public final Ref$IntRef zw;

   public Object invoke() {
      return this.a();
   }

   public final int a() {
      Ref$IntRef var10000;
      int var1;
      int var4;
      if (this.zv.isEmpty()) {
         var10000 = this.zw;
         var10000.element = (var1 = var10000.element) + 1;
         var4 = var1;
      } else {
         Collection var3 = (Collection)this.zv;
         boolean var2 = false;
         Integer var5;
         if (!var3.isEmpty() && Intrinsics.compare(((Number)this.zv.peek()).intValue(), this.zw.element) <= 0) {
            var5 = (Integer)this.zv.pop();
         } else {
            var10000 = this.zw;
            var10000.element = (var1 = var10000.element) + 1;
            var5 = var1;
         }

         var4 = var5;
      }

      return var4;
   }

   public os(Stack var1, Ref$IntRef var2) {
      super(0);
      this.zv = var1;
      this.zw = var2;
   }
}
