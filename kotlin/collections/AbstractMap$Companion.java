package kotlin.collections;

import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000¢\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000e¨\u0006\u000f"},
   d2 = {"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"}
)
public final class AbstractMap$Companion {
   public final int entryHashCode$kotlin_stdlib(@NotNull Entry var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      Object var10000 = var1.getKey();
      int var6 = var10000 != null ? var10000.hashCode() : 0;
      Object var10001 = var1.getValue();
      return var6 ^ (var10001 != null ? var10001.hashCode() : 0);
   }

   @NotNull
   public final String entryToString$kotlin_stdlib(@NotNull Entry var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var5 = false;
      return "" + var1.getKey() + '=' + var1.getValue();
   }

   public final boolean entryEquals$kotlin_stdlib(@NotNull Entry var1, @Nullable Object var2) {
      if (!(var2 instanceof Entry)) {
         return false;
      } else {
         return Intrinsics.areEqual(var1.getKey(), ((Entry)var2).getKey()) && Intrinsics.areEqual(var1.getValue(), ((Entry)var2).getValue());
      }
   }

   private AbstractMap$Companion() {
      super();
   }

   public AbstractMap$Companion(DefaultConstructorMarker var1) {
      this();
   }
}
