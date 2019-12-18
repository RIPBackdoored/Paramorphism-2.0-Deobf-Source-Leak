package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;

public final class ov extends Lambda implements Function1 {
   public final ow bag;

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      ow.a(this.bag).b();
      ClassNode var2 = var1.d();
      ClassNode var3 = new ClassNode();
      var2.accept((ClassVisitor)(new ClassRemapper((ClassVisitor)var3, (Remapper)ow.a(this.bag))));
      var1.a(var3);
      var1.a(ow.a(this.bag).a());
   }

   public ov(ow var1) {
      super(1);
      this.bag = var1;
   }
}
