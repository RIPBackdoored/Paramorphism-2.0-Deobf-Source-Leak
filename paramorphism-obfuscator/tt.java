package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class tt implements ClassSet {
   private final ElementMask bio;
   private final ClassSet bip;

   @Nullable
   public ClassInfo get(@NotNull String var1) {
      return !li.a(this.bio, var1) ? null : this.bip.get(var1);
   }

   public void put(@NotNull ClassInfo var1) {
      throw (Throwable)(new UnsupportedOperationException());
   }

   public boolean contains(@NotNull String var1) {
      return !li.a(this.bio, var1) ? false : this.bip.contains(var1);
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.filter(this.bip.all(), (Function1)(new tu(this)));
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.filter(this.bip.names(), (Function1)(new tn(this)));
   }

   public void close() {
      this.bip.close();
   }

   public tt(@NotNull ElementMask var1, @NotNull ClassSet var2) {
      super();
      this.bio = var1;
      this.bip = var2;
   }

   public static final ElementMask a(tt var0) {
      return var0.bio;
   }
}
