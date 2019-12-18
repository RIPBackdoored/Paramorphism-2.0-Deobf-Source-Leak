package kotlin.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000^\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001d\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0002\b\u000e\u001a(\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a(\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\u0013\u001a.\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n¢\u0006\u0002\u0010\u0014\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a-\u0010\u0016\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0018\u001a&\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0087\b¢\u0006\u0002\u0010\u001b\u001a-\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001c\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a-\u0010\u001e\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\u0006\u001a&\u0010\u001e\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001e\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001e\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010\u001e\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001e\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u0015\u0010\u001f\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0003H\u0002¢\u0006\u0002\b \u001a \u0010!\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\"\u001a\u00020#H\u0007\u001a&\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00020%\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0006\u0010\"\u001a\u00020#H\u0007¨\u0006&"},
   d2 = {"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "shuffle", "random", "Lkotlin/random/Random;", "shuffled", "", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__MutableCollectionsKt extends CollectionsKt__MutableCollectionsJVMKt {
   @InlineOnly
   private static final boolean remove(@NotNull Collection var0, Object var1) {
      return TypeIntrinsics.asMutableCollection(var0).remove(var1);
   }

   @InlineOnly
   private static final boolean removeAll(@NotNull Collection var0, Collection var1) {
      return TypeIntrinsics.asMutableCollection(var0).removeAll(var1);
   }

   @InlineOnly
   private static final boolean retainAll(@NotNull Collection var0, Collection var1) {
      return TypeIntrinsics.asMutableCollection(var0).retainAll(var1);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use removeAt(index) instead.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "removeAt(index)"
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final Object remove(@NotNull List var0, int var1) {
      byte var2 = 0;
      return var0.remove(var1);
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Collection var0, Object var1) {
      byte var2 = 0;
      var0.add(var1);
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Collection var0, Iterable var1) {
      byte var2 = 0;
      CollectionsKt.addAll(var0, var1);
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Collection var0, Object[] var1) {
      byte var2 = 0;
      CollectionsKt.addAll(var0, var1);
   }

   @InlineOnly
   private static final void plusAssign(@NotNull Collection var0, Sequence var1) {
      byte var2 = 0;
      CollectionsKt.addAll(var0, var1);
   }

   @InlineOnly
   private static final void minusAssign(@NotNull Collection var0, Object var1) {
      byte var2 = 0;
      var0.remove(var1);
   }

   @InlineOnly
   private static final void minusAssign(@NotNull Collection var0, Iterable var1) {
      byte var2 = 0;
      CollectionsKt.removeAll(var0, var1);
   }

   @InlineOnly
   private static final void minusAssign(@NotNull Collection var0, Object[] var1) {
      byte var2 = 0;
      CollectionsKt.removeAll(var0, var1);
   }

   @InlineOnly
   private static final void minusAssign(@NotNull Collection var0, Sequence var1) {
      byte var2 = 0;
      CollectionsKt.removeAll(var0, var1);
   }

   public static final boolean addAll(@NotNull Collection var0, @NotNull Iterable var1) {
      if (var1 instanceof Collection) {
         return var0.addAll((Collection)var1);
      } else {
         boolean var3 = false;
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            Object var4 = var5.next();
            if (var0.add(var4)) {
               var3 = true;
            }
         }

         return var3;
      }
   }

   public static final boolean addAll(@NotNull Collection var0, @NotNull Sequence var1) {
      boolean var2 = false;
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Object var3 = var4.next();
         if (var0.add(var3)) {
            var2 = true;
         }
      }

      return var2;
   }

   public static final boolean addAll(@NotNull Collection var0, @NotNull Object[] var1) {
      return var0.addAll((Collection)ArraysKt.asList(var1));
   }

   public static final boolean removeAll(@NotNull Iterable var0, @NotNull Function1 var1) {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, true);
   }

   public static final boolean retainAll(@NotNull Iterable var0, @NotNull Function1 var1) {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, false);
   }

   private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(@NotNull Iterable var0, Function1 var1, boolean var2) {
      boolean var3 = false;
      Iterator var4 = var0.iterator();
      boolean var5 = false;
      boolean var6 = false;
      Iterator var7 = var4;
      boolean var8 = false;

      while(var7.hasNext()) {
         if ((Boolean)var1.invoke(var7.next()) == var2) {
            var7.remove();
            var3 = true;
         }
      }

      return var3;
   }

   public static final boolean removeAll(@NotNull List var0, @NotNull Function1 var1) {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, true);
   }

   public static final boolean retainAll(@NotNull List var0, @NotNull Function1 var1) {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, false);
   }

   private static final boolean filterInPlace$CollectionsKt__MutableCollectionsKt(@NotNull List var0, Function1 var1, boolean var2) {
      if (!(var0 instanceof RandomAccess)) {
         if (var0 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableIterable<T>");
         } else {
            return filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable(var0), var1, var2);
         }
      } else {
         int var3 = 0;
         int var4 = 0;
         int var5 = CollectionsKt.getLastIndex(var0);
         if (var4 <= var5) {
            while(true) {
               Object var6 = var0.get(var4);
               if ((Boolean)var1.invoke(var6) != var2) {
                  if (var3 != var4) {
                     var0.set(var3, var6);
                  }

                  ++var3;
               }

               if (var4 == var5) {
                  break;
               }

               ++var4;
            }
         }

         if (var3 >= var0.size()) {
            return false;
         } else {
            var4 = CollectionsKt.getLastIndex(var0);
            var5 = var3;
            if (var4 >= var3) {
               while(true) {
                  var0.remove(var4);
                  if (var4 == var5) {
                     break;
                  }

                  --var4;
               }
            }

            return true;
         }
      }
   }

   public static final boolean removeAll(@NotNull Collection var0, @NotNull Iterable var1) {
      Collection var3 = CollectionsKt.convertToSetForSetOperationWith(var1, (Iterable)var0);
      boolean var4 = false;
      return TypeIntrinsics.asMutableCollection(var0).removeAll(var3);
   }

   public static final boolean removeAll(@NotNull Collection var0, @NotNull Sequence var1) {
      HashSet var2 = SequencesKt.toHashSet(var1);
      Collection var3 = (Collection)var2;
      boolean var4 = false;
      return !var3.isEmpty() && var0.removeAll((Collection)var2);
   }

   public static final boolean removeAll(@NotNull Collection var0, @NotNull Object[] var1) {
      boolean var3 = false;
      boolean var5 = false;
      return var1.length != 0 && var0.removeAll((Collection)ArraysKt.toHashSet(var1));
   }

   public static final boolean retainAll(@NotNull Collection var0, @NotNull Iterable var1) {
      Collection var3 = CollectionsKt.convertToSetForSetOperationWith(var1, (Iterable)var0);
      boolean var4 = false;
      return TypeIntrinsics.asMutableCollection(var0).retainAll(var3);
   }

   public static final boolean retainAll(@NotNull Collection var0, @NotNull Object[] var1) {
      boolean var3 = false;
      boolean var5 = false;
      return var1.length != 0 ? var0.retainAll((Collection)ArraysKt.toHashSet(var1)) : retainNothing$CollectionsKt__MutableCollectionsKt(var0);
   }

   public static final boolean retainAll(@NotNull Collection var0, @NotNull Sequence var1) {
      HashSet var2 = SequencesKt.toHashSet(var1);
      Collection var3 = (Collection)var2;
      boolean var4 = false;
      return !var3.isEmpty() ? var0.retainAll((Collection)var2) : retainNothing$CollectionsKt__MutableCollectionsKt(var0);
   }

   private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(@NotNull Collection var0) {
      boolean var3 = false;
      boolean var1 = !var0.isEmpty();
      var0.clear();
      return var1;
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final void shuffle(@NotNull List var0, @NotNull Random var1) {
      int var2 = CollectionsKt.getLastIndex(var0);

      for(byte var3 = 1; var2 >= var3; --var2) {
         int var4 = var1.nextInt(var2 + 1);
         Object var5 = var0.get(var2);
         var0.set(var2, var0.get(var4));
         var0.set(var4, var5);
      }

   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final List shuffled(@NotNull Iterable var0, @NotNull Random var1) {
      List var2 = CollectionsKt.toMutableList(var0);
      boolean var3 = false;
      boolean var4 = false;
      boolean var6 = false;
      CollectionsKt.shuffle(var2, var1);
      return var2;
   }

   public CollectionsKt__MutableCollectionsKt() {
      super();
   }
}
