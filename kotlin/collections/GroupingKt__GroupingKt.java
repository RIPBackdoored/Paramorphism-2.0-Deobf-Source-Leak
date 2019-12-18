package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u009b\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u001a´\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b¢\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007¢\u0006\u0002\u0010\u0016\u001a¼\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u001a|\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b¢\u0006\u0002\u0010\u001c\u001aÕ\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b¢\u0006\u0002\u0010\u001e\u001a\u0090\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b¢\u0006\u0002\u0010\u001f\u001a\u0088\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u001a¡\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b¢\u0006\u0002\u0010#¨\u0006$"},
   d2 = {"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"},
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingKt extends GroupingKt__GroupingJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map aggregate(@NotNull Grouping var0, @NotNull Function4 var1) {
      byte var2 = 0;
      Grouping var3 = var0;
      boolean var4 = false;
      Map var10 = (Map)(new LinkedHashMap());
      boolean var5 = false;
      Iterator var6 = var0.sourceIterator();
      boolean var7 = false;
      Iterator var8 = var6;

      while(var8.hasNext()) {
         Object var9 = var8.next();
         Object var11 = var3.keyOf(var9);
         Object var12 = var10.get(var11);
         var10.put(var11, var1.invoke(var11, var12, var9, var12 == null && !var10.containsKey(var11)));
      }

      return var10;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map aggregateTo(@NotNull Grouping var0, @NotNull Map var1, @NotNull Function4 var2) {
      byte var3 = 0;
      Iterator var6 = var0.sourceIterator();
      boolean var7 = false;
      Iterator var5 = var6;

      while(var5.hasNext()) {
         Object var4 = var5.next();
         Object var8 = var0.keyOf(var4);
         Object var9 = var1.get(var8);
         var1.put(var8, var2.invoke(var8, var9, var4, var9 == null && !var1.containsKey(var8)));
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map fold(@NotNull Grouping var0, @NotNull Function2 var1, @NotNull Function3 var2) {
      byte var3 = 0;
      boolean var5 = false;
      Grouping var6 = var0;
      boolean var7 = false;
      Map var21 = (Map)(new LinkedHashMap());
      boolean var8 = false;
      Iterator var9 = var0.sourceIterator();
      boolean var10 = false;
      Iterator var11 = var9;

      while(var11.hasNext()) {
         Object var12 = var11.next();
         Object var22 = var6.keyOf(var12);
         Object var23 = var21.get(var22);
         boolean var13 = var23 == null && !var21.containsKey(var22);
         boolean var17 = false;
         Object var20 = var2.invoke(var22, var13 ? var1.invoke(var22, var12) : var23, var12);
         var21.put(var22, var20);
      }

      return var21;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map foldTo(@NotNull Grouping var0, @NotNull Map var1, @NotNull Function2 var2, @NotNull Function3 var3) {
      byte var4 = 0;
      Grouping var5 = var0;
      boolean var6 = false;
      Iterator var7 = var0.sourceIterator();
      boolean var8 = false;
      Iterator var9 = var7;

      while(var9.hasNext()) {
         Object var10 = var9.next();
         Object var19 = var5.keyOf(var10);
         Object var20 = var1.get(var19);
         boolean var11 = var20 == null && !var1.containsKey(var19);
         boolean var15 = false;
         Object var18 = var3.invoke(var19, var11 ? var2.invoke(var19, var10) : var20, var10);
         var1.put(var19, var18);
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map fold(@NotNull Grouping var0, Object var1, @NotNull Function2 var2) {
      byte var3 = 0;
      boolean var5 = false;
      Grouping var6 = var0;
      boolean var7 = false;
      Map var21 = (Map)(new LinkedHashMap());
      boolean var8 = false;
      Iterator var9 = var0.sourceIterator();
      boolean var10 = false;
      Iterator var11 = var9;

      while(var11.hasNext()) {
         Object var12 = var11.next();
         Object var22 = var6.keyOf(var12);
         Object var23 = var21.get(var22);
         boolean var13 = var23 == null && !var21.containsKey(var22);
         boolean var17 = false;
         Object var20 = var2.invoke(var13 ? var1 : var23, var12);
         var21.put(var22, var20);
      }

      return var21;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map foldTo(@NotNull Grouping var0, @NotNull Map var1, Object var2, @NotNull Function2 var3) {
      byte var4 = 0;
      Grouping var5 = var0;
      boolean var6 = false;
      Iterator var7 = var0.sourceIterator();
      boolean var8 = false;
      Iterator var9 = var7;

      while(var9.hasNext()) {
         Object var10 = var9.next();
         Object var19 = var5.keyOf(var10);
         Object var20 = var1.get(var19);
         boolean var11 = var20 == null && !var1.containsKey(var19);
         boolean var15 = false;
         Object var18 = var3.invoke(var11 ? var2 : var20, var10);
         var1.put(var19, var18);
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map reduce(@NotNull Grouping var0, @NotNull Function3 var1) {
      byte var2 = 0;
      boolean var4 = false;
      Grouping var5 = var0;
      boolean var6 = false;
      Map var20 = (Map)(new LinkedHashMap());
      boolean var7 = false;
      Iterator var8 = var0.sourceIterator();
      boolean var9 = false;
      Iterator var10 = var8;

      while(var10.hasNext()) {
         Object var11 = var10.next();
         Object var21 = var5.keyOf(var11);
         Object var22 = var20.get(var21);
         boolean var12 = var22 == null && !var20.containsKey(var21);
         boolean var16 = false;
         Object var19 = var12 ? var11 : var1.invoke(var21, var22, var11);
         var20.put(var21, var19);
      }

      return var20;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map reduceTo(@NotNull Grouping var0, @NotNull Map var1, @NotNull Function3 var2) {
      byte var3 = 0;
      Grouping var4 = var0;
      boolean var5 = false;
      Iterator var6 = var0.sourceIterator();
      boolean var7 = false;
      Iterator var8 = var6;

      while(var8.hasNext()) {
         Object var9 = var8.next();
         Object var18 = var4.keyOf(var9);
         Object var19 = var1.get(var18);
         boolean var10 = var19 == null && !var1.containsKey(var18);
         boolean var14 = false;
         Object var17 = var10 ? var9 : var2.invoke(var18, var19, var9);
         var1.put(var18, var17);
      }

      return var1;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final Map eachCountTo(@NotNull Grouping var0, @NotNull Map var1) {
      Integer var3 = 0;
      boolean var4 = false;
      Grouping var5 = var0;
      boolean var6 = false;
      Iterator var7 = var0.sourceIterator();
      boolean var8 = false;
      Iterator var9 = var7;

      while(var9.hasNext()) {
         Object var10 = var9.next();
         Object var21 = var5.keyOf(var10);
         Object var22 = var1.get(var21);
         boolean var11 = var22 == null && !var1.containsKey(var21);
         boolean var17 = false;
         int var19 = ((Number)(var11 ? var3 : var22)).intValue();
         boolean var20 = false;
         Integer var23 = var19 + 1;
         var1.put(var21, var23);
      }

      return var1;
   }

   public GroupingKt__GroupingKt() {
      super();
   }
}
