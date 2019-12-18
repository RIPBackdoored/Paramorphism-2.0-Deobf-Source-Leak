package kotlin.collections;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007¢\u0006\u0002\b\u0004\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\b\u001a\u001d\u0010\t\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\n¨\u0006\u000b"},
   d2 = {"asReversed", "", "T", "", "asReversedMutable", "reverseElementIndex", "", "index", "reverseElementIndex$CollectionsKt__ReversedViewsKt", "reversePositionIndex", "reversePositionIndex$CollectionsKt__ReversedViewsKt", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__ReversedViewsKt extends CollectionsKt__MutableCollectionsKt {
   private static final int reverseElementIndex$CollectionsKt__ReversedViewsKt(@NotNull List var0, int var1) {
      int var10000 = CollectionsKt.getLastIndex(var0);
      if (0 <= var1) {
         if (var10000 >= var1) {
            return CollectionsKt.getLastIndex(var0) - var1;
         }
      }

      StringBuilder var10002 = (new StringBuilder()).append("Element index ").append(var1).append(" must be in range [");
      byte var2 = 0;
      throw (Throwable)(new IndexOutOfBoundsException(var10002.append(new IntRange(var2, CollectionsKt.getLastIndex(var0))).append("].").toString()));
   }

   private static final int reversePositionIndex$CollectionsKt__ReversedViewsKt(@NotNull List var0, int var1) {
      int var10000 = var0.size();
      if (0 <= var1) {
         if (var10000 >= var1) {
            return var0.size() - var1;
         }
      }

      StringBuilder var10002 = (new StringBuilder()).append("Position index ").append(var1).append(" must be in range [");
      byte var2 = 0;
      throw (Throwable)(new IndexOutOfBoundsException(var10002.append(new IntRange(var2, var0.size())).append("].").toString()));
   }

   @NotNull
   public static final List asReversed(@NotNull List var0) {
      return (List)(new ReversedListReadOnly(var0));
   }

   @JvmName(
      name = "asReversedMutable"
   )
   @NotNull
   public static final List asReversedMutable(@NotNull List var0) {
      return (List)(new ReversedList(var0));
   }

   public static final int access$reverseElementIndex(List var0, int var1) {
      return reverseElementIndex$CollectionsKt__ReversedViewsKt(var0, var1);
   }

   public static final int access$reversePositionIndex(List var0, int var1) {
      return reversePositionIndex$CollectionsKt__ReversedViewsKt(var0, var1);
   }

   public CollectionsKt__ReversedViewsKt() {
      super();
   }
}
