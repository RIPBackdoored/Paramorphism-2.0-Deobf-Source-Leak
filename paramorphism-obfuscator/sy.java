package paramorphism-obfuscator;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;
import site.hackery.paramorphism.api.resources.ClassInfo;
import site.hackery.paramorphism.api.resources.ClassSet;
import site.hackery.paramorphism.api.resources.Resource;
import site.hackery.paramorphism.api.resources.ResourceSet;

public final class sy implements ClassSet {
   private final ResourceSet bhp;
   private final int bhq;

   @Nullable
   public ClassInfo get(@NotNull String var1) {
      Resource var10000 = this.bhp.get(var1 + ".class");
      if (var10000 != null) {
         Resource var2 = var10000;
         return this.a(var1, var2);
      } else {
         return null;
      }
   }

   public boolean contains(@NotNull String var1) {
      return this.bhp.contains(var1 + ".class");
   }

   @NotNull
   public Sequence all() {
      return SequencesKt.map(SequencesKt.filter(this.bhp.all(), (Function1)ta.bhs), (Function1)(new tb(this)));
   }

   @NotNull
   public Sequence names() {
      return SequencesKt.map(SequencesKt.filter(this.bhp.all(), (Function1)sx.bho), (Function1)sw.bhn);
   }

   public void close() {
      this.bhp.close();
   }

   private final ClassInfo a(String var1, Resource var2) {
      ClassNode var3 = new ClassNode();
      (new ClassReader(var2.getData())).accept((ClassVisitor)var3, this.bhq);
      return new ClassInfo(var1, var3, var2.getOrder());
   }

   public void put(@NotNull ClassInfo var1) {
      throw (Throwable)(new UnsupportedOperationException());
   }

   public sy(@NotNull ResourceSet var1, int var2) {
      super();
      this.bhp = var1;
      this.bhq = var2;
   }

   public sy(ResourceSet var1, int var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = 8;
      }

      this(var1, var2);
   }

   public static final ClassInfo a(sy var0, String var1, Resource var2) {
      return var0.a(var1, var2);
   }
}
