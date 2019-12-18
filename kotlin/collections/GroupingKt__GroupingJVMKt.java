package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aW\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\b¨\u0006\r"},
   d2 = {"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"},
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map eachCount(@NotNull Grouping var0) {
      boolean var2 = false;
      Map var28 = (Map)(new LinkedHashMap());
      boolean var3 = false;
      Grouping var4 = var0;
      boolean var5 = false;
      Iterator var6 = var0.sourceIterator();
      boolean var7 = false;
      Iterator var8 = var6;

      boolean var10;
      Object var32;
      while(var8.hasNext()) {
         Object var9 = var8.next();
         var32 = var4.keyOf(var9);
         Object var33 = var28.get(var32);
         var10 = var33 == null && !var28.containsKey(var32);
         boolean var16 = false;
         Object var10001;
         if (var10) {
            boolean var19 = false;
            Ref$IntRef var27 = new Ref$IntRef();
            var10001 = var27;
         } else {
            var10001 = var33;
         }

         Ref$IntRef var17 = (Ref$IntRef)var10001;
         boolean var20 = false;
         boolean var22 = false;
         boolean var23 = false;
         boolean var25 = false;
         ++var17.element;
         var28.put(var32, var17);
      }

      Map var1 = var28;
      var2 = false;
      Iterable var29 = (Iterable)var1.entrySet();
      boolean var30 = false;
      Iterator var31 = var29.iterator();

      while(var31.hasNext()) {
         var32 = var31.next();
         Entry var34 = (Entry)var32;
         boolean var35 = false;
         if (var34 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
         }

         Entry var26 = TypeIntrinsics.asMutableMapEntry(var34);
         var10 = false;
         Integer var36 = ((Ref$IntRef)var34.getValue()).element;
         var26.setValue(var36);
      }

      return TypeIntrinsics.asMutableMap(var1);
   }

   @PublishedApi
   @InlineOnly
   private static final Map mapValuesInPlace(@NotNull Map var0, Function1 var1) {
      byte var2 = 0;
      Iterable var3 = (Iterable)var0.entrySet();
      boolean var4 = false;
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         Object var6 = var5.next();
         Entry var7 = (Entry)var6;
         boolean var8 = false;
         if (var7 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
         }

         TypeIntrinsics.asMutableMapEntry(var7).setValue(var1.invoke(var7));
      }

      if (var0 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, R>");
      } else {
         return TypeIntrinsics.asMutableMap(var0);
      }
   }

   public GroupingKt__GroupingJVMKt() {
      super();
   }
}
