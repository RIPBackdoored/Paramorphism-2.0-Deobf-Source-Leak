package paramorphism-obfuscator;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class oa extends Lambda implements Function0 {
   public final ow xp;
   public final kg xq;

   public oa(ow var1, kg var2) {
      super(0);
      this.xp = var1;
      this.xq = var2;
   }

   public Object invoke() {
      return this.a();
   }

   @NotNull
   public final Set a() {
      boolean var1 = false;
      Set var15 = (Set)(new LinkedHashSet());
      boolean var2 = false;
      boolean var3 = false;
      Set var4 = var15;
      boolean var5 = false;
      ClassSet[] var6 = new ClassSet[]{this.xq.b(), this.xq.c()};
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ClassSet var9 = var6[var8];
         Sequence var10 = SequencesKt.filter(SequencesKt.map(var9.all(), (Function1)ou.baf), ow.a(this.xp, this.xq));
         boolean var11 = false;
         boolean var12 = false;
         boolean var14 = false;
         CollectionsKt.addAll((Collection)var4, var10);
      }

      return var15;
   }
}
