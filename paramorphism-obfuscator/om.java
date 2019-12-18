package paramorphism-obfuscator;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

public final class om {
   @NotNull
   public static final Sequence a(@NotNull String var0, @NotNull char... var1) {
      return SequencesKt.sequence((Function2)(new of(var0, var1, (Continuation)null)));
   }
}
