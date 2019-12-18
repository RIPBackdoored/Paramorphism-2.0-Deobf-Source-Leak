package paramorphism-obfuscator;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

public final class nx extends Lambda implements Function1 {
   public static final nx xh = new nx();

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      ClassNode var2 = var1.d();
      Iterator var4 = var2.methods.iterator();

      while(true) {
         MethodNode var3;
         do {
            if (!var4.hasNext()) {
               return;
            }

            var3 = (MethodNode)var4.next();
         } while(var3.instructions.size() == 0);

         Lazy var10000 = LazyKt.lazy((Function0)(new nz(var3)));
         KProperty var6 = nv.xe[0];
         Lazy var5 = var10000;
         AbstractInsnNode[] var9 = var3.instructions.toArray();
         int var10 = var9.length;

         for(int var8 = 0; var8 < var10; ++var8) {
            AbstractInsnNode var7 = var9[var8];
            if (var7 instanceof JumpInsnNode) {
               LabelNode var11 = ((JumpInsnNode)var7).label;
               LabelNode var12 = new LabelNode();
               LabelNode var13 = new LabelNode();
               Pair var14 = xg.a((Function1)(new ny(var13, var7, var11, var5, var6, var12)));
               var3.instructions.add((InsnList)var14.getFirst());
               var3.tryCatchBlocks.addAll((Collection)var14.getSecond());
               var3.instructions.insertBefore(var7, (AbstractInsnNode)(new JumpInsnNode(Opcodes.GOTO, var13)));
               var3.instructions.insert(var7, (AbstractInsnNode)var12);
               var3.instructions.remove(var7);
            }
         }
      }
   }

   public nx() {
      super(1);
   }
}
