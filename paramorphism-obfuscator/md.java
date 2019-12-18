package paramorphism-obfuscator;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import site.hackery.paramorphism.api.resources.ClassInfo;

public class md extends mb {
   private boolean uq;
   @NotNull
   private ClassInfo ur;

   public final boolean b() {
      return this.uq;
   }

   public final void a(boolean var1) {
      this.uq = var1;
   }

   @NotNull
   public final String c() {
      return this.ur.getOriginalName();
   }

   @NotNull
   public final ClassNode d() {
      return this.ur.getNode();
   }

   public final void a(@NotNull ClassNode var1) {
      this.ur = new ClassInfo(this.ur.getOriginalName(), var1, this.ur.getOrder());
   }

   @NotNull
   public final ClassInfo e() {
      return this.ur;
   }

   public final void a(@NotNull ClassInfo var1) {
      this.ur = var1;
   }

   public md(@NotNull ClassInfo var1, @NotNull mq var2) {
      super(var2);
      this.ur = var1;
   }
}
