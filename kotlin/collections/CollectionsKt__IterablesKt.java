package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.TuplesKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a \u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\b\u001a\u00020\u0007H\u0001\u001a\u001f\u0010\t\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0001¢\u0006\u0002\u0010\n\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a,\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0010\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a\u001d\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\fH\u0002¢\u0006\u0002\b\u0013\u001a@\u0010\u0014\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00160\u00100\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00160\u00150\u0001¨\u0006\u0017"},
   d2 = {"Iterable", "", "T", "iterator", "Lkotlin/Function0;", "", "collectionSizeOrDefault", "", "default", "collectionSizeOrNull", "(Ljava/lang/Iterable;)Ljava/lang/Integer;", "convertToSetForSetOperation", "", "convertToSetForSetOperationWith", "source", "flatten", "", "safeToConvertToSet", "", "safeToConvertToSet$CollectionsKt__IterablesKt", "unzip", "Lkotlin/Pair;", "R", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__IterablesKt extends CollectionsKt__CollectionsKt {
   @InlineOnly
   private static final Iterable Iterable(Function0 var0) {
      byte var1 = 0;
      return (Iterable)(new CollectionsKt__IterablesKt$Iterable$1(var0));
   }

   @PublishedApi
   @Nullable
   public static final Integer collectionSizeOrNull(@NotNull Iterable var0) {
      return var0 instanceof Collection ? ((Collection)var0).size() : null;
   }

   @PublishedApi
   public static final int collectionSizeOrDefault(@NotNull Iterable var0, int var1) {
      return var0 instanceof Collection ? ((Collection)var0).size() : var1;
   }

   private static final boolean safeToConvertToSet$CollectionsKt__IterablesKt(@NotNull Collection var0) {
      return var0.size() > 2 && var0 instanceof ArrayList;
   }

   @NotNull
   public static final Collection convertToSetForSetOperationWith(@NotNull Iterable var0, @NotNull Iterable var1) {
      return var0 instanceof Set ? (Collection)var0 : (var0 instanceof Collection ? (var1 instanceof Collection && ((Collection)var1).size() < 2 ? (Collection)var0 : (safeToConvertToSet$CollectionsKt__IterablesKt((Collection)var0) ? (Collection)CollectionsKt.toHashSet(var0) : (Collection)var0)) : (Collection)CollectionsKt.toHashSet(var0));
   }

   @NotNull
   public static final Collection convertToSetForSetOperation(@NotNull Iterable var0) {
      return var0 instanceof Set ? (Collection)var0 : (var0 instanceof Collection ? (safeToConvertToSet$CollectionsKt__IterablesKt((Collection)var0) ? (Collection)CollectionsKt.toHashSet(var0) : (Collection)var0) : (Collection)CollectionsKt.toHashSet(var0));
   }

   @NotNull
   public static final List flatten(@NotNull Iterable var0) {
      ArrayList var1 = new ArrayList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Iterable var2 = (Iterable)var3.next();
         CollectionsKt.addAll((Collection)var1, var2);
      }

      return (List)var1;
   }

   @NotNull
   public static final Pair unzip(@NotNull Iterable var0) {
      int var1 = CollectionsKt.collectionSizeOrDefault(var0, 10);
      ArrayList var2 = new ArrayList(var1);
      ArrayList var3 = new ArrayList(var1);
      Iterator var5 = var0.iterator();

      while(var5.hasNext()) {
         Pair var4 = (Pair)var5.next();
         var2.add(var4.getFirst());
         var3.add(var4.getSecond());
      }

      return TuplesKt.to(var2, var3);
   }

   public CollectionsKt__IterablesKt() {
      super();
   }
}
