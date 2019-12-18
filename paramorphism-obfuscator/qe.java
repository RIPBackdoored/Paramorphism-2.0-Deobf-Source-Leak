package paramorphism-obfuscator;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class qe extends Lambda implements Function1 {
   public final qc bcq;
   public final kg bcr;
   public final Random bcs;

   public Object invoke(Object var1) {
      this.a((lx)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull lx var1) {
      Function1 var2 = qc.a(this.bcq, this.bcr);
      List var3 = SequencesKt.toList(SequencesKt.filter(this.bcr.b().all(), (Function1)(new qg(var2))));
      Collection var4 = (Collection)var3;
      qc var12 = this.bcq;
      boolean var5 = false;
      boolean var13 = !var4.isEmpty();
      qc var10000 = var12;
      String var10001;
      if (var13) {
         var5 = false;
         boolean var6 = false;
         boolean var8 = false;
         ClassInfo var18 = (ClassInfo)var3.get(this.bcs.nextInt(var3.size()));
         String var14 = var18.getNode().name;
         var5 = false;
         int var15 = StringsKt.getLastIndex((CharSequence)var14);

         String var20;
         label66: {
            for(boolean var7 = false; var15 >= 0; --var15) {
               char var16 = var14.charAt(var15);
               boolean var9 = false;
               if (var16 == '/') {
                  byte var17 = 0;
                  int var10 = var15 + 1;
                  boolean var11 = false;
                  var20 = var14.substring(var17, var10);
                  break label66;
               }
            }

            var20 = "";
         }

         String var19 = var20;
         var10000 = var12;
         var10001 = var19;
      } else {
         var10001 = "dev/paramorphism/runtime/methods/";
      }

      qc.a(var10000, var10001);
   }

   public qe(qc var1, kg var2, Random var3) {
      super(1);
      this.bcq = var1;
      this.bcr = var2;
      this.bcs = var3;
   }
}
