package paramorphism-obfuscator;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.wrappers.ClassWrapper;
import paramorphism-obfuscator.wrappers.MethodWrapper;

public final class qr extends Lambda implements Function1 {
   public final qb bdp;
   public final ClassWrapper bdq;
   public final FieldNode bdr;
   public final MethodNode bds;

   public Object invoke(Object var1) {
      this.a((MethodWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull MethodWrapper param1) {
      // $FF: Couldn't be decompiled
   }

   public qr(qb var1, ClassWrapper var2, FieldNode var3, MethodNode var4) {
      super(1);
      this.bdp = var1;
      this.bdq = var2;
      this.bdr = var3;
      this.bds = var4;
   }
}
