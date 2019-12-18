package kotlin;

import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\f\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0000\n\u0000\u001a\u000f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0087\b¨\u0006\u0003"},
   d2 = {"hashCode", "", "", "kotlin-stdlib"}
)
public final class HashCodeKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final int hashCode(@Nullable Object var0) {
      byte var1 = 0;
      return var0 != null ? var0.hashCode() : 0;
   }
}
