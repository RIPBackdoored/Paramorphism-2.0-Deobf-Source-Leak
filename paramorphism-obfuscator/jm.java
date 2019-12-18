package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

public final class jm extends Lambda implements Function0 {
   public final jg pt;

   public Object invoke() {
      return this.a();
   }

   @Nullable
   public final ju a() {
      ju var10000;
      if (this.pt.e("shaded_libraries") != null) {
         boolean var2 = false;
         boolean var3 = false;
         boolean var5 = false;
         var10000 = new ju("shaded_libraries", (jn)this.pt);
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public jm(jg var1) {
      super(0);
      this.pt = var1;
   }
}
