package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class ub implements ClassSet {
   private final Map biy;
   private final ClassSet biz;

   @Nullable
   public ClassInfo get(@NotNull String var1) {
      if (this.biy.containsKey(var1)) {
         return (ClassInfo)this.biy.get(var1);
      } else {
         ClassSet var10000 = this.biz;
         return var10000 != null ? var10000.get(var1) : null;
      }
   }

   public void put(@NotNull ClassInfo var1) {
      this.biy.put(var1.getOriginalName(), var1);
   }

   public boolean contains(@NotNull String var1) {
      boolean var2;
      if (!this.biy.containsKey(var1)) {
         ClassSet var10000 = this.biz;
         if (!(var10000 != null ? var10000.contains(var1) : false)) {
            var2 = false;
            return var2;
         }
      }

      var2 = true;
      return var2;
   }

   @NotNull
   public Sequence all() {
      Sequence var1;
      Sequence var8;
      label27: {
         var1 = CollectionsKt.asSequence((Iterable)this.biy.values());
         ClassSet var10000 = this.biz;
         if (var10000 != null) {
            ClassSet var3 = var10000;
            boolean var4 = false;
            boolean var5 = false;
            boolean var7 = false;
            var8 = SequencesKt.filter(var3.all(), (Function1)(new tz(this)));
            if (var8 != null) {
               break label27;
            }
         }

         var8 = SequencesKt.emptySequence();
      }

      Sequence var2 = var8;
      return SequencesKt.plus(var1, var2);
   }

   @NotNull
   public Sequence names() {
      Sequence var1;
      Sequence var8;
      label27: {
         var1 = CollectionsKt.asSequence((Iterable)this.biy.keySet());
         ClassSet var10000 = this.biz;
         if (var10000 != null) {
            ClassSet var3 = var10000;
            boolean var4 = false;
            boolean var5 = false;
            boolean var7 = false;
            var8 = SequencesKt.filter(var3.names(), (Function1)(new ua(this)));
            if (var8 != null) {
               break label27;
            }
         }

         var8 = SequencesKt.emptySequence();
      }

      Sequence var2 = var8;
      return SequencesKt.plus(var1, var2);
   }

   public void close() {
      ClassSet var10000 = this.biz;
      if (var10000 != null) {
         var10000.close();
      }

   }

   public ub(@Nullable ClassSet var1) {
      super();
      this.biz = var1;
      boolean var2 = false;
      Map var4 = (Map)(new LinkedHashMap());
      this.biy = var4;
   }

   public static final Map a(ub var0) {
      return var0.biy;
   }
}
