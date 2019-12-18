package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class sz implements ResourceSet {
   private final ResourceSet bhr;

   @Nullable
   public Resource get(@NotNull String var1) {
      return StringsKt.endsWith$default(var1, ".class", false, 2, (Object)null) ? null : this.bhr.get(var1);
   }

   public void put(@NotNull String var1, @Nullable Resource var2) {
      this.bhr.put(var1, var2);
   }

   public boolean contains(@NotNull String var1) {
      return StringsKt.endsWith$default(var1, ".class", false, 2, (Object)null) ? false : this.bhr.contains(var1);
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.filter(this.bhr.all(), (Function1)st.bhl);
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.filter(this.bhr.names(), (Function1)su.bhm);
   }

   public void close() {
      this.bhr.close();
   }

   public sz(@NotNull ResourceSet var1) {
      super();
      this.bhr = var1;
   }
}
