package paramorphism-obfuscator;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class tx implements ResourceSet {
   private final Map bit;
   private final ResourceSet biu;

   @Nullable
   public Resource get(@NotNull String var1) {
      if (this.bit.containsKey(var1)) {
         return (Resource)this.bit.get(var1);
      } else {
         ResourceSet var10000 = this.biu;
         return var10000 != null ? var10000.get(var1) : null;
      }
   }

   public void put(@NotNull String var1, @Nullable Resource var2) {
      this.bit.put(var1, var2);
   }

   public boolean contains(@NotNull String var1) {
      boolean var2;
      if (!this.bit.containsKey(var1)) {
         ResourceSet var10000 = this.biu;
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
         var1 = SequencesKt.mapNotNull(CollectionsKt.asSequence((Iterable)this.bit.entrySet()), (Function1)ty.biv);
         ResourceSet var10000 = this.biu;
         if (var10000 != null) {
            ResourceSet var3 = var10000;
            boolean var4 = false;
            boolean var5 = false;
            boolean var7 = false;
            var8 = SequencesKt.filter(var3.all(), (Function1)(new tw(this)));
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
         var1 = CollectionsKt.asSequence((Iterable)this.bit.keySet());
         ResourceSet var10000 = this.biu;
         if (var10000 != null) {
            ResourceSet var3 = var10000;
            boolean var4 = false;
            boolean var5 = false;
            boolean var7 = false;
            var8 = SequencesKt.filter(var3.names(), (Function1)(new uc(this)));
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
      ResourceSet var10000 = this.biu;
      if (var10000 != null) {
         var10000.close();
      }

   }

   public tx(@Nullable ResourceSet var1) {
      super();
      this.biu = var1;
      boolean var2 = false;
      Map var4 = (Map)(new LinkedHashMap());
      this.bit = var4;
   }

   public static final Map a(tx var0) {
      return var0.bit;
   }
}
