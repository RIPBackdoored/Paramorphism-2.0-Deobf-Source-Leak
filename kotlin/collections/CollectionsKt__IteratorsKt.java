package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005H\u0086\b\u001a\u001f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\n\u001a\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\b0\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003¨\u0006\t"},
   d2 = {"forEach", "", "T", "", "operation", "Lkotlin/Function1;", "iterator", "withIndex", "Lkotlin/collections/IndexedValue;", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__IteratorsKt extends CollectionsKt__IteratorsJVMKt {
   @InlineOnly
   private static final Iterator iterator(@NotNull Iterator var0) {
      byte var1 = 0;
      return var0;
   }

   @NotNull
   public static final Iterator withIndex(@NotNull Iterator var0) {
      return (Iterator)(new IndexingIterator(var0));
   }

   public static final void forEach(@NotNull Iterator var0, @NotNull Function1 var1) {
      byte var2 = 0;
      boolean var6 = false;
      Iterator var4 = var0;

      while(var4.hasNext()) {
         Object var3 = var4.next();
         var1.invoke(var3);
      }

   }

   public CollectionsKt__IteratorsKt() {
      super();
   }
}
