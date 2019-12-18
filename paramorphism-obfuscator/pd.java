package paramorphism-obfuscator;

import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.MethodNode;

public final class pd extends Lambda implements Function1 {
   public static final pd baw = new pd();

   public Object invoke(Object var1) {
      return this.a((MethodNode)var1);
   }

   @NotNull
   public final Sequence a(MethodNode var1) {
      return SequencesKt.asSequence((Iterator)var1.instructions.iterator());
   }

   public pd() {
      super(1);
   }
}
