package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0010\u001e\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\bJ\u0019\u0010\t\u001a\u00020\n2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0000¢\u0006\u0002\b\f¨\u0006\r"},
   d2 = {"Lkotlin/collections/AbstractSet$Companion;", "", "()V", "setEquals", "", "c", "", "other", "setEquals$kotlin_stdlib", "unorderedHashCode", "", "", "unorderedHashCode$kotlin_stdlib", "kotlin-stdlib"}
)
public final class AbstractSet$Companion {
   public final int unorderedHashCode$kotlin_stdlib(@NotNull Collection var1) {
      int var2 = 0;

      Object var3;
      for(Iterator var4 = var1.iterator(); var4.hasNext(); var2 += var3 != null ? var3.hashCode() : 0) {
         var3 = var4.next();
      }

      return var2;
   }

   public final boolean setEquals$kotlin_stdlib(@NotNull Set var1, @NotNull Set var2) {
      return var1.size() != var2.size() ? false : var1.containsAll((Collection)var2);
   }

   private AbstractSet$Companion() {
      super();
   }

   public AbstractSet$Companion(DefaultConstructorMarker var1) {
      this();
   }
}
