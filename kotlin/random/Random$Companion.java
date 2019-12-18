package kotlin.random;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;

/** @deprecated */
@Deprecated(
   message = "Use Default companion object instead",
   level = DeprecationLevel.HIDDEN
)
@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"},
   d2 = {"Lkotlin/random/Random$Companion;", "Lkotlin/random/Random;", "()V", "nextBits", "", "bitCount", "kotlin-stdlib"}
)
public final class Random$Companion extends Random {
   public static final Random$Companion INSTANCE;

   public int nextBits(int var1) {
      return Random.Default.nextBits(var1);
   }

   private Random$Companion() {
      super();
   }

   static {
      Random$Companion var0 = new Random$Companion();
      INSTANCE = var0;
   }
}
