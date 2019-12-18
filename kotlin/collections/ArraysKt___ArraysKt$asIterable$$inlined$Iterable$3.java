package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0011\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0096\u0002¨\u0006\u0004¸\u0006\u0000"},
   d2 = {"kotlin/collections/CollectionsKt__IterablesKt$Iterable$1", "", "iterator", "", "kotlin-stdlib"}
)
public final class ArraysKt___ArraysKt$asIterable$$inlined$Iterable$3 implements Iterable, KMappedMarker {
   final short[] $this_asIterable$inlined;

   public ArraysKt___ArraysKt$asIterable$$inlined$Iterable$3(short[] var1) {
      super();
      this.$this_asIterable$inlined = var1;
   }

   @NotNull
   public Iterator iterator() {
      boolean var1 = false;
      return (Iterator)ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
   }
}
