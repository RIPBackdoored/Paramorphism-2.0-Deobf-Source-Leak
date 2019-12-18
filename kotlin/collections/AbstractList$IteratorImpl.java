package kotlin.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0092\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\t\u0010\t\u001a\u00020\nH\u0096\u0002J\u000e\u0010\u000b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"},
   d2 = {"Lkotlin/collections/AbstractList$IteratorImpl;", "", "(Lkotlin/collections/AbstractList;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}
)
class AbstractList$IteratorImpl implements Iterator, KMappedMarker {
   private int index;
   final AbstractList this$0;

   protected final int getIndex() {
      return this.index;
   }

   protected final void setIndex(int var1) {
      this.index = var1;
   }

   public boolean hasNext() {
      return this.index < this.this$0.size();
   }

   public Object next() {
      if (!this.hasNext()) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         AbstractList var10000 = this.this$0;
         int var1;
         this.index = (var1 = this.index) + 1;
         return var10000.get(var1);
      }
   }

   public AbstractList$IteratorImpl(AbstractList var1) {
      super();
      this.this$0 = var1;
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
