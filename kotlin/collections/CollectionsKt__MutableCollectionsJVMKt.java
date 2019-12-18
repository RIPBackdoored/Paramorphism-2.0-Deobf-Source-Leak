package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0005\u001a\u0019\u0010\u0006\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b\u001a!\u0010\u0006\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0087\b\u001a\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0007\u001a&\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a \u0010\f\u001a\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a3\u0010\f\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00100\u000fH\u0087\b\u001a5\u0010\f\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0013H\u0087\b\u001a2\u0010\u0014\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0013¨\u0006\u0015"},
   d2 = {"fill", "", "T", "", "value", "(Ljava/util/List;Ljava/lang/Object;)V", "shuffle", "random", "Ljava/util/Random;", "shuffled", "", "", "sort", "", "comparison", "Lkotlin/Function2;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "sortWith", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__MutableCollectionsJVMKt extends CollectionsKt__IteratorsKt {
   /** @deprecated */
   @Deprecated(
      message = "Use sortWith(comparator) instead.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "this.sortWith(comparator)"
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final void sort(@NotNull List var0, Comparator var1) {
      byte var2 = 0;
      throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
   }

   /** @deprecated */
   @Deprecated(
      message = "Use sortWith(Comparator(comparison)) instead.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "this.sortWith(Comparator(comparison))"
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final void sort(@NotNull List var0, Function2 var1) {
      byte var2 = 0;
      throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
   }

   public static final void sort(@NotNull List var0) {
      if (var0.size() > 1) {
         Collections.sort(var0);
      }

   }

   public static final void sortWith(@NotNull List var0, @NotNull Comparator var1) {
      if (var0.size() > 1) {
         Collections.sort(var0, var1);
      }

   }

   @InlineOnly
   @SinceKotlin(
      version = "1.2"
   )
   private static final void fill(@NotNull List var0, Object var1) {
      byte var2 = 0;
      Collections.fill(var0, var1);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.2"
   )
   private static final void shuffle(@NotNull List var0) {
      byte var1 = 0;
      Collections.shuffle(var0);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.2"
   )
   private static final void shuffle(@NotNull List var0, Random var1) {
      byte var2 = 0;
      Collections.shuffle(var0, var1);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List shuffled(@NotNull Iterable var0) {
      List var1 = CollectionsKt.toMutableList(var0);
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      boolean var7 = false;
      Collections.shuffle(var1);
      return var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final List shuffled(@NotNull Iterable var0, @NotNull Random var1) {
      List var2 = CollectionsKt.toMutableList(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      boolean var9 = false;
      Collections.shuffle(var2, var1);
      return var2;
   }

   public CollectionsKt__MutableCollectionsJVMKt() {
      super();
   }
}
