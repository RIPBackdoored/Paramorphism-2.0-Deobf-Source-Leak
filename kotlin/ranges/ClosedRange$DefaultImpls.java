package kotlin.ranges;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class ClosedRange$DefaultImpls {
   public static boolean contains(ClosedRange var0, @NotNull Comparable var1) {
      return var1.compareTo(var0.getStart()) >= 0 && var1.compareTo(var0.getEndInclusive()) <= 0;
   }

   public static boolean isEmpty(ClosedRange var0) {
      return var0.getStart().compareTo(var0.getEndInclusive()) > 0;
   }
}
