package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000x\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a@\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a@\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a\u001f\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a5\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u0019\u001a\u0012\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007\u001a\u0015\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u001c\u001a%\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010 \u001a3\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020\u001e2\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00070\u0018\"\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010\u001c\u001a\u0015\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u001c\u001a%\u0010\"\u001a\u00020#2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006H\u0002¢\u0006\u0002\b&\u001a\b\u0010'\u001a\u00020#H\u0001\u001a\b\u0010(\u001a\u00020#H\u0001\u001a%\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018H\u0000¢\u0006\u0002\u0010*\u001aS\u0010+\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\u0006\u0010\u001f\u001a\u0002H\u00072\u001a\u0010,\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00070-j\n\u0012\u0006\b\u0000\u0012\u0002H\u0007`.2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006¢\u0006\u0002\u0010/\u001a>\u0010+\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00060\u000e\u001aE\u0010+\u001a\u00020\u0006\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u000701*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\b2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u00072\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006¢\u0006\u0002\u00102\u001ad\u00103\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007\"\u000e\b\u0001\u00104*\b\u0012\u0004\u0012\u0002H401*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\u00105\u001a\u0004\u0018\u0001H42\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0016\b\u0004\u00106\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u0001H40\u000eH\u0086\b¢\u0006\u0002\u00107\u001a,\u00108\u001a\u000209\"\t\b\u0000\u0010\u0007¢\u0006\u0002\b:*\b\u0012\u0004\u0012\u0002H\u00070\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a8\u0010;\u001a\u0002H<\"\u0010\b\u0000\u0010=*\u0006\u0012\u0002\b\u00030\u0002*\u0002H<\"\u0004\b\u0001\u0010<*\u0002H=2\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H<0?H\u0087\b¢\u0006\u0002\u0010@\u001a\u0019\u0010A\u001a\u000209\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a,\u0010B\u001a\u000209\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001e\u0010C\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\bH\u0000\u001a!\u0010D\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u001a!\u0010D\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\bH\u0087\b\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006E"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "throwCountOverflow", "throwIndexOverflow", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "optimizeReadOnlyList", "orEmpty", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
   @NotNull
   public static final Collection asCollection(@NotNull Object[] var0) {
      return (Collection)(new ArrayAsCollection(var0, false));
   }

   @NotNull
   public static final List emptyList() {
      return (List)EmptyList.INSTANCE;
   }

   @NotNull
   public static final List listOf(@NotNull Object... var0) {
      return var0.length > 0 ? ArraysKt.asList(var0) : CollectionsKt.emptyList();
   }

   @InlineOnly
   private static final List listOf() {
      byte var0 = 0;
      return CollectionsKt.emptyList();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final List mutableListOf() {
      byte var0 = 0;
      return (List)(new ArrayList());
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final ArrayList arrayListOf() {
      byte var0 = 0;
      return new ArrayList();
   }

   @NotNull
   public static final List mutableListOf(@NotNull Object... var0) {
      return var0.length == 0 ? (List)(new ArrayList()) : (List)(new ArrayList((Collection)(new ArrayAsCollection(var0, true))));
   }

   @NotNull
   public static final ArrayList arrayListOf(@NotNull Object... var0) {
      return var0.length == 0 ? new ArrayList() : new ArrayList((Collection)(new ArrayAsCollection(var0, true)));
   }

   @NotNull
   public static final List listOfNotNull(@Nullable Object var0) {
      return var0 != null ? CollectionsKt.listOf(var0) : CollectionsKt.emptyList();
   }

   @NotNull
   public static final List listOfNotNull(@NotNull Object... var0) {
      return ArraysKt.filterNotNull(var0);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final List List(int var0, Function1 var1) {
      byte var2 = 0;
      boolean var3 = false;
      ArrayList var4 = new ArrayList(var0);
      boolean var5 = false;
      boolean var6 = false;
      int var10 = 0;

      for(int var7 = var0; var10 < var7; ++var10) {
         boolean var9 = false;
         var4.add(var1.invoke(var10));
      }

      return (List)var4;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final List MutableList(int var0, Function1 var1) {
      byte var2 = 0;
      ArrayList var3 = new ArrayList(var0);
      boolean var4 = false;
      boolean var5 = false;
      int var9 = 0;

      for(int var6 = var0; var9 < var6; ++var9) {
         boolean var8 = false;
         var3.add(var1.invoke(var9));
      }

      return (List)var3;
   }

   @NotNull
   public static final IntRange getIndices(@NotNull Collection var0) {
      byte var1 = 0;
      return new IntRange(var1, var0.size() - 1);
   }

   public static final int getLastIndex(@NotNull List var0) {
      return var0.size() - 1;
   }

   @InlineOnly
   private static final boolean isNotEmpty(@NotNull Collection var0) {
      byte var1 = 0;
      return !var0.isEmpty();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean isNullOrEmpty(@Nullable Collection var0) {
      byte var1 = 0;
      boolean var2 = false;
      return var0 == null || var0.isEmpty();
   }

   @InlineOnly
   private static final Collection orEmpty(@Nullable Collection var0) {
      byte var1 = 0;
      Collection var10000 = var0;
      if (var0 == null) {
         var10000 = (Collection)CollectionsKt.emptyList();
      }

      return var10000;
   }

   @InlineOnly
   private static final List orEmpty(@Nullable List var0) {
      byte var1 = 0;
      List var10000 = var0;
      if (var0 == null) {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final Object ifEmpty(Collection var0, Function0 var1) {
      byte var2 = 0;
      return var0.isEmpty() ? var1.invoke() : var0;
   }

   @InlineOnly
   private static final boolean containsAll(@NotNull Collection var0, Collection var1) {
      byte var2 = 0;
      return var0.containsAll(var1);
   }

   @NotNull
   public static final List optimizeReadOnlyList(@NotNull List var0) {
      List var10000;
      switch(var0.size()) {
      case 0:
         var10000 = CollectionsKt.emptyList();
         break;
      case 1:
         var10000 = CollectionsKt.listOf(var0.get(0));
         break;
      default:
         var10000 = var0;
      }

      return var10000;
   }

   public static final int binarySearch(@NotNull List var0, @Nullable Comparable var1, int var2, int var3) {
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var2, var3);
      int var4 = var2;
      int var5 = var3 - 1;

      while(var4 <= var5) {
         int var6 = var4 + var5 >>> 1;
         Comparable var7 = (Comparable)var0.get(var6);
         int var8 = ComparisonsKt.compareValues(var7, var1);
         if (var8 < 0) {
            var4 = var6 + 1;
         } else {
            if (var8 <= 0) {
               return var6;
            }

            var5 = var6 - 1;
         }
      }

      return -(var4 + 1);
   }

   public static int binarySearch$default(List var0, Comparable var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   public static final int binarySearch(@NotNull List var0, Object var1, @NotNull Comparator var2, int var3, int var4) {
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var3, var4);
      int var5 = var3;
      int var6 = var4 - 1;

      while(var5 <= var6) {
         int var7 = var5 + var6 >>> 1;
         Object var8 = var0.get(var7);
         int var9 = var2.compare(var8, var1);
         if (var9 < 0) {
            var5 = var7 + 1;
         } else {
            if (var9 <= 0) {
               return var7;
            }

            var6 = var7 - 1;
         }
      }

      return -(var5 + 1);
   }

   public static int binarySearch$default(List var0, Object var1, Comparator var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3, var4);
   }

   public static final int binarySearchBy(@NotNull List var0, @Nullable Comparable var1, int var2, int var3, @NotNull Function1 var4) {
      byte var5 = 0;
      return CollectionsKt.binarySearch(var0, var2, var3, (Function1)(new CollectionsKt__CollectionsKt$binarySearchBy$1(var4, var1)));
   }

   public static int binarySearchBy$default(List var0, Comparable var1, int var2, int var3, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0;
      }

      if ((var5 & 4) != 0) {
         var3 = var0.size();
      }

      boolean var7 = false;
      return CollectionsKt.binarySearch(var0, var2, var3, (Function1)(new CollectionsKt__CollectionsKt$binarySearchBy$1(var4, var1)));
   }

   public static final int binarySearch(@NotNull List var0, int var1, int var2, @NotNull Function1 var3) {
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var1, var2);
      int var4 = var1;
      int var5 = var2 - 1;

      while(var4 <= var5) {
         int var6 = var4 + var5 >>> 1;
         Object var7 = var0.get(var6);
         int var8 = ((Number)var3.invoke(var7)).intValue();
         if (var8 < 0) {
            var4 = var6 + 1;
         } else {
            if (var8 <= 0) {
               return var6;
            }

            var5 = var6 - 1;
         }
      }

      return -(var4 + 1);
   }

   public static int binarySearch$default(List var0, int var1, int var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   private static final void rangeCheck$CollectionsKt__CollectionsKt(int var0, int var1, int var2) {
      if (var1 > var2) {
         throw (Throwable)(new IllegalArgumentException("fromIndex (" + var1 + ") is greater than toIndex (" + var2 + ")."));
      } else if (var1 < 0) {
         throw (Throwable)(new IndexOutOfBoundsException("fromIndex (" + var1 + ") is less than zero."));
      } else if (var2 > var0) {
         throw (Throwable)(new IndexOutOfBoundsException("toIndex (" + var2 + ") is greater than size (" + var0 + ")."));
      }
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwIndexOverflow() {
      throw (Throwable)(new ArithmeticException("Index overflow has happened."));
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwCountOverflow() {
      throw (Throwable)(new ArithmeticException("Count overflow has happened."));
   }

   public CollectionsKt__CollectionsKt() {
      super();
   }
}
