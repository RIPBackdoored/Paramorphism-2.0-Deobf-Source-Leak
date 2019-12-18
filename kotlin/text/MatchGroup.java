package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
   d2 = {"Lkotlin/text/MatchGroup;", "", "value", "", "range", "Lkotlin/ranges/IntRange;", "(Ljava/lang/String;Lkotlin/ranges/IntRange;)V", "getRange", "()Lkotlin/ranges/IntRange;", "getValue", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "kotlin-stdlib"}
)
public final class MatchGroup {
   @NotNull
   private final String value;
   @NotNull
   private final IntRange range;

   @NotNull
   public final String getValue() {
      return this.value;
   }

   @NotNull
   public final IntRange getRange() {
      return this.range;
   }

   public MatchGroup(@NotNull String var1, @NotNull IntRange var2) {
      super();
      this.value = var1;
      this.range = var2;
   }

   @NotNull
   public final String component1() {
      return this.value;
   }

   @NotNull
   public final IntRange component2() {
      return this.range;
   }

   @NotNull
   public final MatchGroup copy(@NotNull String var1, @NotNull IntRange var2) {
      return new MatchGroup(var1, var2);
   }

   public static MatchGroup copy$default(MatchGroup var0, String var1, IntRange var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.value;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.range;
      }

      return var0.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return "MatchGroup(value=" + this.value + ", range=" + this.range + ")";
   }

   public int hashCode() {
      String var10000 = this.value;
      int var1 = (var10000 != null ? var10000.hashCode() : 0) * 31;
      IntRange var10001 = this.range;
      return var1 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof MatchGroup) {
            MatchGroup var2 = (MatchGroup)var1;
            if (Intrinsics.areEqual((Object)this.value, (Object)var2.value) && Intrinsics.areEqual((Object)this.range, (Object)var2.range)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
