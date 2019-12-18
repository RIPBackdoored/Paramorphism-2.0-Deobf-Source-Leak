package paramorphism-obfuscator;

import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class ud implements ResourceSet {
   private final ResourceSet bjb;
   private final ResourceSet bjc;

   @Nullable
   public Resource get(@NotNull String var1) {
      Resource var10000 = this.bjb.get(var1);
      if (var10000 == null) {
         var10000 = this.bjc.get(var1);
      }

      return var10000;
   }

   public void put(@NotNull String var1, @Nullable Resource var2) {
      try {
         this.bjb.put(var1, var2);
      } catch (Exception var4) {
         this.bjc.put(var1, var2);
      }

   }

   public boolean contains(@NotNull String var1) {
      return this.bjb.contains(var1) || this.bjc.contains(var1);
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.plus(this.bjb.all(), this.bjc.all());
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.plus(this.bjb.names(), this.bjc.names());
   }

   public void close() {
      this.bjb.close();
      this.bjc.close();
   }

   public ud(@NotNull ResourceSet var1, @NotNull ResourceSet var2) {
      super();
      this.bjb = var1;
      this.bjc = var2;
   }
}
