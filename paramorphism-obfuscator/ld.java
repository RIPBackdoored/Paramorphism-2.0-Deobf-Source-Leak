package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class ld extends Lambda implements Function0 {
   public final lc tn;

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final Map a() {
      Sequence var1 = lc.a(this.tn).all();
      boolean var2 = false;
      Map var4 = (Map)(new LinkedHashMap());
      boolean var5 = false;
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         Object var7 = var6.next();
         ClassInfo var9 = (ClassInfo)var7;
         boolean var10 = false;
         String var10000 = var9.getOriginalName();
         le var10001 = this.tn.a(var9.getNode().name);
         if (var10001 == null) {
            var10001 = this.tn.a(var9.getOriginalName());
         }

         if (var10001 == null) {
            Intrinsics.throwNpe();
         }

         Pair var11 = TuplesKt.to(var10000, var10001);
         var10 = false;
         var4.put(var11.getFirst(), var11.getSecond());
      }

      return var4;
   }

   public ld(lc var1) {
      super(0);
      this.tn = var1;
   }
}
