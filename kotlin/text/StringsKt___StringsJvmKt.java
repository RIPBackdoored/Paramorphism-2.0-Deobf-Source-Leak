package kotlin.text;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\f\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0010\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006*\u00020\u0002¨\u0006\u0007"},
   d2 = {"elementAt", "", "", "index", "", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt___StringsJvmKt extends StringsKt__StringsKt {
   @InlineOnly
   private static final char elementAt(@NotNull CharSequence var0, int var1) {
      byte var2 = 0;
      return var0.charAt(var1);
   }

   @NotNull
   public static final SortedSet toSortedSet(@NotNull CharSequence var0) {
      return (SortedSet)StringsKt.toCollection(var0, (Collection)(new TreeSet()));
   }

   public StringsKt___StringsJvmKt() {
      super();
   }
}
