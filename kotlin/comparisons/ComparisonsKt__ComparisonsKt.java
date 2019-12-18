package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a;\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aY\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\t\u001aW\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a;\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001aW\u0010\f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001a-\u0010\r\u001a\u00020\u000e\"\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00062\b\u0010\u000f\u001a\u0004\u0018\u0001H\u00022\b\u0010\u0010\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0011\u001a>\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b¢\u0006\u0002\u0010\u0013\u001aY\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u000226\u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\b\"\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\u0014\u001aZ\u0010\u0012\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n2\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b¢\u0006\u0002\u0010\u0015\u001aG\u0010\u0016\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u000f\u001a\u0002H\u00022\u0006\u0010\u0010\u001a\u0002H\u00022 \u0010\u0007\u001a\u001c\u0012\u0018\b\u0001\u0012\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u00050\bH\u0002¢\u0006\u0004\b\u0017\u0010\u0014\u001a&\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a-\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u0019\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a-\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0087\b\u001a@\u0010\u001b\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001j\n\u0012\u0006\u0012\u0004\u0018\u0001H\u0002`\u0003\"\b\b\u0000\u0010\u0002*\u00020\u001a2\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003\u001a&\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0006\u001a0\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\u001aO\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004\u001aO\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010\u001f\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001aO\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\b\u0004\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00060\u0005H\u0087\b\u001ak\u0010 \u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\n0\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\n`\u00032\u0014\b\u0004\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\n0\u0005H\u0087\b\u001am\u0010!\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u000328\b\u0004\u0010\"\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000e0#H\u0087\b\u001aO\u0010&\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u0003\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u0004\u0012\u0002H\u00020\u0001j\b\u0012\u0004\u0012\u0002H\u0002`\u00032\u001a\u0010\u000b\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0001j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0003H\u0086\u0004¨\u0006'"},
   d2 = {"compareBy", "Ljava/util/Comparator;", "T", "Lkotlin/Comparator;", "selector", "Lkotlin/Function1;", "", "selectors", "", "([Lkotlin/jvm/functions/Function1;)Ljava/util/Comparator;", "K", "comparator", "compareByDescending", "compareValues", "", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)I", "compareValuesBy", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;[Lkotlin/jvm/functions/Function1;)I", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;Lkotlin/jvm/functions/Function1;)I", "compareValuesByImpl", "compareValuesByImpl$ComparisonsKt__ComparisonsKt", "naturalOrder", "nullsFirst", "", "nullsLast", "reverseOrder", "reversed", "then", "thenBy", "thenByDescending", "thenComparator", "comparison", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "thenDescending", "kotlin-stdlib"},
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt__ComparisonsKt {
   public static final int compareValuesBy(Object var0, Object var1, @NotNull Function1... var2) {
      boolean var3 = var2.length > 0;
      boolean var4 = false;
      boolean var5 = false;
      var5 = false;
      boolean var6 = false;
      if (!var3) {
         boolean var7 = false;
         String var8 = "Failed requirement.";
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         return compareValuesByImpl$ComparisonsKt__ComparisonsKt(var0, var1, var2);
      }
   }

   private static final int compareValuesByImpl$ComparisonsKt__ComparisonsKt(Object var0, Object var1, Function1[] var2) {
      Function1[] var5 = var2;
      int var6 = var2.length;

      for(int var4 = 0; var4 < var6; ++var4) {
         Function1 var3 = var5[var4];
         Comparable var7 = (Comparable)var3.invoke(var0);
         Comparable var8 = (Comparable)var3.invoke(var1);
         int var9 = ComparisonsKt.compareValues(var7, var8);
         if (var9 != 0) {
            return var9;
         }
      }

      return 0;
   }

   @InlineOnly
   private static final int compareValuesBy(Object var0, Object var1, Function1 var2) {
      byte var3 = 0;
      return ComparisonsKt.compareValues((Comparable)var2.invoke(var0), (Comparable)var2.invoke(var1));
   }

   @InlineOnly
   private static final int compareValuesBy(Object var0, Object var1, Comparator var2, Function1 var3) {
      byte var4 = 0;
      return var2.compare(var3.invoke(var0), var3.invoke(var1));
   }

   public static final int compareValues(@Nullable Comparable var0, @Nullable Comparable var1) {
      if (var0 == var1) {
         return 0;
      } else if (var0 == null) {
         return -1;
      } else {
         return var1 == null ? 1 : var0.compareTo(var1);
      }
   }

   @NotNull
   public static final Comparator compareBy(@NotNull Function1... var0) {
      boolean var1 = var0.length > 0;
      boolean var2 = false;
      boolean var3 = false;
      var3 = false;
      boolean var4 = false;
      if (!var1) {
         boolean var5 = false;
         String var6 = "Failed requirement.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      } else {
         return (Comparator)(new ComparisonsKt__ComparisonsKt$compareBy$1(var0));
      }
   }

   @InlineOnly
   private static final Comparator compareBy(Function1 var0) {
      byte var1 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$compareBy$2(var0));
   }

   @InlineOnly
   private static final Comparator compareBy(Comparator var0, Function1 var1) {
      byte var2 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$compareBy$3(var0, var1));
   }

   @InlineOnly
   private static final Comparator compareByDescending(Function1 var0) {
      byte var1 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$compareByDescending$1(var0));
   }

   @InlineOnly
   private static final Comparator compareByDescending(Comparator var0, Function1 var1) {
      byte var2 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$compareByDescending$2(var0, var1));
   }

   @InlineOnly
   private static final Comparator thenBy(@NotNull Comparator var0, Function1 var1) {
      byte var2 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$thenBy$1(var0, var1));
   }

   @InlineOnly
   private static final Comparator thenBy(@NotNull Comparator var0, Comparator var1, Function1 var2) {
      byte var3 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$thenBy$2(var0, var1, var2));
   }

   @InlineOnly
   private static final Comparator thenByDescending(@NotNull Comparator var0, Function1 var1) {
      byte var2 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$thenByDescending$1(var0, var1));
   }

   @InlineOnly
   private static final Comparator thenByDescending(@NotNull Comparator var0, Comparator var1, Function1 var2) {
      byte var3 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$thenByDescending$2(var0, var1, var2));
   }

   @InlineOnly
   private static final Comparator thenComparator(@NotNull Comparator var0, Function2 var1) {
      byte var2 = 0;
      return (Comparator)(new ComparisonsKt__ComparisonsKt$thenComparator$1(var0, var1));
   }

   @NotNull
   public static final Comparator then(@NotNull Comparator var0, @NotNull Comparator var1) {
      return (Comparator)(new ComparisonsKt__ComparisonsKt$then$1(var0, var1));
   }

   @NotNull
   public static final Comparator thenDescending(@NotNull Comparator var0, @NotNull Comparator var1) {
      return (Comparator)(new ComparisonsKt__ComparisonsKt$thenDescending$1(var0, var1));
   }

   @NotNull
   public static final Comparator nullsFirst(@NotNull Comparator var0) {
      return (Comparator)(new ComparisonsKt__ComparisonsKt$nullsFirst$1(var0));
   }

   @InlineOnly
   private static final Comparator nullsFirst() {
      byte var0 = 0;
      return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
   }

   @NotNull
   public static final Comparator nullsLast(@NotNull Comparator var0) {
      return (Comparator)(new ComparisonsKt__ComparisonsKt$nullsLast$1(var0));
   }

   @InlineOnly
   private static final Comparator nullsLast() {
      byte var0 = 0;
      return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
   }

   @NotNull
   public static final Comparator naturalOrder() {
      NaturalOrderComparator var10000 = NaturalOrderComparator.INSTANCE;
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
      } else {
         return (Comparator)var10000;
      }
   }

   @NotNull
   public static final Comparator reverseOrder() {
      ReverseOrderComparator var10000 = ReverseOrderComparator.INSTANCE;
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
      } else {
         return (Comparator)var10000;
      }
   }

   @NotNull
   public static final Comparator reversed(@NotNull Comparator var0) {
      Comparator var10000;
      if (var0 instanceof ReversedComparator) {
         var10000 = ((ReversedComparator)var0).getComparator();
      } else if (Intrinsics.areEqual((Object)var0, (Object)NaturalOrderComparator.INSTANCE)) {
         ReverseOrderComparator var2 = ReverseOrderComparator.INSTANCE;
         if (var2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
         }

         var10000 = (Comparator)var2;
      } else if (Intrinsics.areEqual((Object)var0, (Object)ReverseOrderComparator.INSTANCE)) {
         NaturalOrderComparator var3 = NaturalOrderComparator.INSTANCE;
         if (var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Comparator<T> /* = java.util.Comparator<T> */");
         }

         var10000 = (Comparator)var3;
      } else {
         var10000 = (Comparator)(new ReversedComparator(var0));
      }

      return var10000;
   }

   public static final int access$compareValuesByImpl(Object var0, Object var1, Function1[] var2) {
      return compareValuesByImpl$ComparisonsKt__ComparisonsKt(var0, var1, var2);
   }

   public ComparisonsKt__ComparisonsKt() {
      super();
   }
}
