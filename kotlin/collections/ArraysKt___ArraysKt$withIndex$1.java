package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n¢\u0006\u0002\b\u0003"},
   d2 = {"<anonymous>", "", "T", "invoke"}
)
final class ArraysKt___ArraysKt$withIndex$1 extends Lambda implements Function0 {
   final Object[] $this_withIndex;

   public Object invoke() {
      return this.invoke();
   }

   @NotNull
   public final Iterator invoke() {
      return ArrayIteratorKt.iterator(this.$this_withIndex);
   }

   ArraysKt___ArraysKt$withIndex$1(Object[] var1) {
      super(0);
      this.$this_withIndex = var1;
   }
}
