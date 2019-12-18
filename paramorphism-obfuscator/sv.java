package paramorphism-obfuscator;

import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class sv implements ResourceSet {
   @Nullable
   public Void a(@NotNull String var1) {
      return null;
   }

   public Resource get(String var1) {
      return (Resource)this.a(var1);
   }

   public void put(@NotNull String var1, @Nullable Resource var2) {
      throw (Throwable)(new UnsupportedOperationException());
   }

   public boolean contains(@NotNull String var1) {
      return false;
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.emptySequence();
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.emptySequence();
   }

   public void close() {
   }

   public sv() {
      super();
   }
}
