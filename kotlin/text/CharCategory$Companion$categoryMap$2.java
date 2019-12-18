package kotlin.text;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001H\n¢\u0006\u0002\b\u0004"},
   d2 = {"<anonymous>", "", "", "Lkotlin/text/CharCategory;", "invoke"}
)
final class CharCategory$Companion$categoryMap$2 extends Lambda implements Function0 {
   public static final CharCategory$Companion$categoryMap$2 INSTANCE = new CharCategory$Companion$categoryMap$2();

   public Object invoke() {
      return this.invoke();
   }

   @NotNull
   public final Map invoke() {
      CharCategory[] var1 = CharCategory.values();
      boolean var2 = false;
      int var3 = RangesKt.coerceAtLeast(MapsKt.mapCapacity(var1.length), 16);
      Map var5 = (Map)(new LinkedHashMap(var3));
      boolean var6 = false;
      CharCategory[] var7 = var1;
      int var8 = var1.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         CharCategory var10 = var7[var9];
         boolean var12 = false;
         Integer var14 = var10.getValue();
         var5.put(var14, var10);
      }

      return var5;
   }

   CharCategory$Companion$categoryMap$2() {
      super(0);
   }
}
