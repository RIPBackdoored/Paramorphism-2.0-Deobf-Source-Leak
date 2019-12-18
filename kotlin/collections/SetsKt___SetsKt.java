package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004¨\u0006\r"},
   d2 = {"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"},
   xs = "kotlin/collections/SetsKt"
)
class SetsKt___SetsKt extends SetsKt__SetsKt {
   @NotNull
   public static final Set minus(@NotNull Set var0, Object var1) {
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size()));
      boolean var3 = false;
      Iterable var4 = (Iterable)var0;
      boolean var5 = false;
      Iterator var6 = var4.iterator();

      while(var6.hasNext()) {
         Object var7 = var6.next();
         boolean var9 = false;
         boolean var10000;
         if (!var3 && Intrinsics.areEqual(var7, var1)) {
            var3 = true;
            var10000 = false;
         } else {
            var10000 = true;
         }

         if (var10000) {
            ((Collection)var2).add(var7);
         }
      }

      return (Set)((Collection)var2);
   }

   @NotNull
   public static final Set minus(@NotNull Set var0, @NotNull Object[] var1) {
      LinkedHashSet var2 = new LinkedHashSet((Collection)var0);
      CollectionsKt.removeAll((Collection)var2, var1);
      return (Set)var2;
   }

   @NotNull
   public static final Set minus(@NotNull Set var0, @NotNull Iterable var1) {
      Collection var2 = CollectionsKt.convertToSetForSetOperationWith(var1, (Iterable)var0);
      if (var2.isEmpty()) {
         return CollectionsKt.toSet((Iterable)var0);
      } else if (var2 instanceof Set) {
         Iterable var10 = (Iterable)var0;
         Collection var4 = (Collection)(new LinkedHashSet());
         boolean var5 = false;
         Iterator var6 = var10.iterator();

         while(var6.hasNext()) {
            Object var7 = var6.next();
            boolean var9 = false;
            if (!var2.contains(var7)) {
               var4.add(var7);
            }
         }

         return (Set)var4;
      } else {
         LinkedHashSet var3 = new LinkedHashSet((Collection)var0);
         var3.removeAll(var2);
         return (Set)var3;
      }
   }

   @NotNull
   public static final Set minus(@NotNull Set var0, @NotNull Sequence var1) {
      LinkedHashSet var2 = new LinkedHashSet((Collection)var0);
      CollectionsKt.removeAll((Collection)var2, var1);
      return (Set)var2;
   }

   @InlineOnly
   private static final Set minusElement(@NotNull Set var0, Object var1) {
      byte var2 = 0;
      return SetsKt.minus(var0, var1);
   }

   @NotNull
   public static final Set plus(@NotNull Set var0, Object var1) {
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size() + 1));
      var2.addAll((Collection)var0);
      var2.add(var1);
      return (Set)var2;
   }

   @NotNull
   public static final Set plus(@NotNull Set var0, @NotNull Object[] var1) {
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size() + var1.length));
      var2.addAll((Collection)var0);
      CollectionsKt.addAll((Collection)var2, var1);
      return (Set)var2;
   }

   @NotNull
   public static final Set plus(@NotNull Set var0, @NotNull Iterable var1) {
      Integer var10000 = CollectionsKt.collectionSizeOrNull(var1);
      int var12;
      if (var10000 != null) {
         Integer var3 = var10000;
         boolean var4 = false;
         boolean var5 = false;
         int var6 = ((Number)var3).intValue();
         boolean var7 = false;
         int var10 = var0.size() + var6;
         var12 = var10;
      } else {
         var12 = var0.size() * 2;
      }

      int var11 = MapsKt.mapCapacity(var12);
      LinkedHashSet var2 = new LinkedHashSet(var11);
      var2.addAll((Collection)var0);
      CollectionsKt.addAll((Collection)var2, var1);
      return (Set)var2;
   }

   @NotNull
   public static final Set plus(@NotNull Set var0, @NotNull Sequence var1) {
      LinkedHashSet var2 = new LinkedHashSet(MapsKt.mapCapacity(var0.size() * 2));
      var2.addAll((Collection)var0);
      CollectionsKt.addAll((Collection)var2, var1);
      return (Set)var2;
   }

   @InlineOnly
   private static final Set plusElement(@NotNull Set var0, Object var1) {
      byte var2 = 0;
      return SetsKt.plus(var0, var1);
   }

   public SetsKt___SetsKt() {
      super();
   }
}
