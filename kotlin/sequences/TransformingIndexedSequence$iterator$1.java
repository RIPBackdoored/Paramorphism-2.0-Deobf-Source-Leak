package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001b\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u000b\u001a\u00020\fH\u0096\u0002J\u000e\u0010\r\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"},
   d2 = {"kotlin/sequences/TransformingIndexedSequence$iterator$1", "", "index", "", "getIndex", "()I", "setIndex", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}
)
public final class TransformingIndexedSequence$iterator$1 implements Iterator, KMappedMarker {
   @NotNull
   private final Iterator iterator;
   private int index;
   final TransformingIndexedSequence this$0;

   @NotNull
   public final Iterator getIterator() {
      return this.iterator;
   }

   public final int getIndex() {
      return this.index;
   }

   public final void setIndex(int var1) {
      this.index = var1;
   }

   public Object next() {
      Function2 var10000 = TransformingIndexedSequence.access$getTransformer$p(this.this$0);
      int var1;
      this.index = (var1 = this.index) + 1;
      Function2 var3 = var10000;
      boolean var2 = false;
      if (var1 < 0) {
         CollectionsKt.throwIndexOverflow();
      }

      return var3.invoke(var1, this.iterator.next());
   }

   public boolean hasNext() {
      return this.iterator.hasNext();
   }

   TransformingIndexedSequence$iterator$1(TransformingIndexedSequence var1) {
      super();
      this.this$0 = var1;
      this.iterator = TransformingIndexedSequence.access$getSequence$p(var1).iterator();
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
