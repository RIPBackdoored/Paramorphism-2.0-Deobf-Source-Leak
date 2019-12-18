package kotlin.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u00000\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0005\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001a\u001f\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0004j\b\u0012\u0004\u0012\u0002H\u0002`\u0005\"\u0004\b\u0000\u0010\u0002H\u0087\b\u001a5\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\u0004j\b\u0012\u0004\u0012\u0002H\u0002`\u0005\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0007\"\u0002H\u0002¢\u0006\u0002\u0010\b\u001a\u001f\u0010\t\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\nj\b\u0012\u0004\u0012\u0002H\u0002`\u000b\"\u0004\b\u0000\u0010\u0002H\u0087\b\u001a5\u0010\t\u001a\u0012\u0012\u0004\u0012\u0002H\u00020\nj\b\u0012\u0004\u0012\u0002H\u0002`\u000b\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0007\"\u0002H\u0002¢\u0006\u0002\u0010\f\u001a\u0015\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000e\"\u0004\b\u0000\u0010\u0002H\u0087\b\u001a+\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000e\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0007\"\u0002H\u0002¢\u0006\u0002\u0010\u000f\u001a\u0015\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\u0087\b\u001a+\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0007\"\u0002H\u0002¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a!\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b¨\u0006\u0013"},
   d2 = {"emptySet", "", "T", "hashSetOf", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "elements", "", "([Ljava/lang/Object;)Ljava/util/HashSet;", "linkedSetOf", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "([Ljava/lang/Object;)Ljava/util/LinkedHashSet;", "mutableSetOf", "", "([Ljava/lang/Object;)Ljava/util/Set;", "setOf", "optimizeReadOnlySet", "orEmpty", "kotlin-stdlib"},
   xs = "kotlin/collections/SetsKt"
)
class SetsKt__SetsKt extends SetsKt__SetsJVMKt {
   @NotNull
   public static final Set emptySet() {
      return (Set)EmptySet.INSTANCE;
   }

   @NotNull
   public static final Set setOf(@NotNull Object... var0) {
      return var0.length > 0 ? ArraysKt.toSet(var0) : SetsKt.emptySet();
   }

   @InlineOnly
   private static final Set setOf() {
      byte var0 = 0;
      return SetsKt.emptySet();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final Set mutableSetOf() {
      byte var0 = 0;
      return (Set)(new LinkedHashSet());
   }

   @NotNull
   public static final Set mutableSetOf(@NotNull Object... var0) {
      return (Set)ArraysKt.toCollection(var0, (Collection)(new LinkedHashSet(MapsKt.mapCapacity(var0.length))));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final HashSet hashSetOf() {
      byte var0 = 0;
      return new HashSet();
   }

   @NotNull
   public static final HashSet hashSetOf(@NotNull Object... var0) {
      return (HashSet)ArraysKt.toCollection(var0, (Collection)(new HashSet(MapsKt.mapCapacity(var0.length))));
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final LinkedHashSet linkedSetOf() {
      byte var0 = 0;
      return new LinkedHashSet();
   }

   @NotNull
   public static final LinkedHashSet linkedSetOf(@NotNull Object... var0) {
      return (LinkedHashSet)ArraysKt.toCollection(var0, (Collection)(new LinkedHashSet(MapsKt.mapCapacity(var0.length))));
   }

   @InlineOnly
   private static final Set orEmpty(@Nullable Set var0) {
      byte var1 = 0;
      Set var10000 = var0;
      if (var0 == null) {
         var10000 = SetsKt.emptySet();
      }

      return var10000;
   }

   @NotNull
   public static final Set optimizeReadOnlySet(@NotNull Set var0) {
      Set var10000;
      switch(var0.size()) {
      case 0:
         var10000 = SetsKt.emptySet();
         break;
      case 1:
         var10000 = SetsKt.setOf(var0.iterator().next());
         break;
      default:
         var10000 = var0;
      }

      return var10000;
   }

   public SetsKt__SetsKt() {
      super();
   }
}
