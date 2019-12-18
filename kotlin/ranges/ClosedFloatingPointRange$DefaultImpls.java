package kotlin.ranges;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class ClosedFloatingPointRange$DefaultImpls {
   public static boolean contains(ClosedFloatingPointRange var0, @NotNull Comparable var1) {
      return var0.lessThanOrEquals(var0.getStart(), var1) && var0.lessThanOrEquals(var1, var0.getEndInclusive());
   }

   public static boolean isEmpty(ClosedFloatingPointRange var0) {
      return !var0.lessThanOrEquals(var0.getStart(), var0.getEndInclusive());
   }
}
