package paramorphism-obfuscator;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

public final class ll implements lr {
   private final int tx;
   private final Type ty;

   public boolean a(@NotNull AbstractInsnNode var1) {
      return var1 instanceof TypeInsnNode && (Intrinsics.areEqual((Object)((TypeInsnNode)var1).desc, (Object)this.ty.getDescriptor()) || Intrinsics.areEqual((Object)((TypeInsnNode)var1).desc, (Object)this.ty.getInternalName())) && (this.tx == 0 || ((TypeInsnNode)var1).getOpcode() == this.tx);
   }

   public ll(int var1, @NotNull Type var2) {
      super();
      this.tx = var1;
      this.ty = var2;
   }
}
