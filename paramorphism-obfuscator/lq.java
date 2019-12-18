package paramorphism-obfuscator;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

public final class lq {
   private static final int ud = 0;

   @NotNull
   public static final Sequence a(@NotNull InsnList var0, @NotNull lr[] var1) {
      AbstractInsnNode[] var2 = var0.toArray();
      Ref$IntRef var3 = new Ref$IntRef();
      var3.element = 0;
      return SequencesKt.generateSequence((Function0)(new ls(var2, var3, var1)));
   }
}
