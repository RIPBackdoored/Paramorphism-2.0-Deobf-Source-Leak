package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.hackery.paramorphism.api.config.ElementMask;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class tr implements ResourceSet {
   private final ElementMask bij;
   private final ResourceSet bik;
   private final Function1 bil;

   @Nullable
   public Resource get(@NotNull String var1) {
      return !li.a(this.bij, (String)this.bil.invoke(var1)) ? null : this.bik.get(var1);
   }

   public void put(@NotNull String var1, @Nullable Resource var2) {
      throw (Throwable)(new UnsupportedOperationException());
   }

   public boolean contains(@NotNull String var1) {
      return !li.a(this.bij, (String)this.bil.invoke(var1)) ? false : this.bik.contains(var1);
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.filter(this.bik.all(), (Function1)(new tp(this)));
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.filter(this.bik.names(), (Function1)(new to(this)));
   }

   public void close() {
      this.bik.close();
   }

   public tr(@NotNull ElementMask var1, @NotNull ResourceSet var2, @NotNull Function1 var3) {
      super();
      this.bij = var1;
      this.bik = var2;
      this.bil = var3;
   }

   public tr(ElementMask var1, ResourceSet var2, Function1 var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 4) != 0) {
         var3 = (Function1)tq.bii;
      }

      this(var1, var2, var3);
   }

   public static final ElementMask a(tr var0) {
      return var0.bij;
   }

   public static final Function1 b(tr var0) {
      return var0.bil;
   }
}
