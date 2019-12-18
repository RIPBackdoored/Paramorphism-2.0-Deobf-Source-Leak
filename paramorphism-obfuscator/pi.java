package paramorphism-obfuscator;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

public final class pi extends Lambda implements Function1 {
   public static final pi bba = new pi();

   public Object invoke(Object var1) {
      return this.a((ClassNode)var1);
   }

   @NotNull
   public final Sequence a(@NotNull ClassNode var1) {
      return CollectionsKt.asSequence((Iterable)var1.methods);
   }

   public pi() {
      super(1);
   }
}
