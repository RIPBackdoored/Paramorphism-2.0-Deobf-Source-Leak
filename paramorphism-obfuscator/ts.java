package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class ts implements ClassSet {
   private final ElementMask bim;
   private final ClassSet bin;

   @Nullable
   public ClassInfo get(@NotNull String var1) {
      return li.a(this.bim, var1) ? null : this.bin.get(var1);
   }

   public void put(@NotNull ClassInfo var1) {
      throw (Throwable)(new UnsupportedOperationException());
   }

   public boolean contains(@NotNull String var1) {
      return li.a(this.bim, var1) ? false : this.bin.contains(var1);
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.filter(this.bin.all(), (Function1)(new tm(this)));
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.filter(this.bin.names(), (Function1)(new tv(this)));
   }

   public void close() {
      this.bin.close();
   }

   public ts(@NotNull ElementMask var1, @NotNull ClassSet var2) {
      super();
      this.bim = var1;
      this.bin = var2;
   }

   public static final ElementMask a(ts var0) {
      return var0.bim;
   }
}
