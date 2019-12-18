package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000#\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u000b\u001a\u00020\fH\u0002J\t\u0010\r\u001a\u00020\u000eH\u0096\u0002J\u000e\u0010\u000f\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0011"},
   d2 = {"kotlin/sequences/DropSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "left", "", "getLeft", "()I", "setLeft", "(I)V", "drop", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}
)
public final class DropSequence$iterator$1 implements Iterator, KMappedMarker {
   @NotNull
   private final Iterator iterator;
   private int left;
   final DropSequence this$0;

   @NotNull
   public final Iterator getIterator() {
      return this.iterator;
   }

   public final int getLeft() {
      return this.left;
   }

   public final void setLeft(int var1) {
      this.left = var1;
   }

   private final void drop() {
      while(this.left > 0 && this.iterator.hasNext()) {
         this.iterator.next();
         this.left += -1;
      }

   }

   public Object next() {
      this.drop();
      return this.iterator.next();
   }

   public boolean hasNext() {
      this.drop();
      return this.iterator.hasNext();
   }

   DropSequence$iterator$1(DropSequence var1) {
      super();
      this.this$0 = var1;
      this.iterator = DropSequence.access$getSequence$p(var1).iterator();
      this.left = DropSequence.access$getCount$p(var1);
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
