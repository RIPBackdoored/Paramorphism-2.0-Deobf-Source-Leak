package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KVariance;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class TypeReference$WhenMappings {
   public static final int[] $EnumSwitchMapping$0 = new int[KVariance.values().length];

   static {
      $EnumSwitchMapping$0[KVariance.INVARIANT.ordinal()] = 1;
      $EnumSwitchMapping$0[KVariance.IN.ordinal()] = 2;
      $EnumSwitchMapping$0[KVariance.OUT.ordinal()] = 3;
   }
}
