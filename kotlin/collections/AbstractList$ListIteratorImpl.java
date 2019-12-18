package kotlin.collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010*\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0092\u0004\u0018\u00002\f0\u0001R\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0005H\u0016J\r\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\u0005H\u0016¨\u0006\r"},
   d2 = {"Lkotlin/collections/AbstractList$ListIteratorImpl;", "Lkotlin/collections/AbstractList$IteratorImpl;", "Lkotlin/collections/AbstractList;", "", "index", "", "(Lkotlin/collections/AbstractList;I)V", "hasPrevious", "", "nextIndex", "previous", "()Ljava/lang/Object;", "previousIndex", "kotlin-stdlib"}
)
class AbstractList$ListIteratorImpl extends AbstractList$IteratorImpl implements ListIterator, KMappedMarker {
   final AbstractList this$0;

   public boolean hasPrevious() {
      return this.getIndex() > 0;
   }

   public int nextIndex() {
      return this.getIndex();
   }

   public Object previous() {
      if (!this.hasPrevious()) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         AbstractList var10000 = this.this$0;
         this.setIndex(this.getIndex() + -1);
         return var10000.get(this.getIndex());
      }
   }

   public int previousIndex() {
      return this.getIndex() - 1;
   }

   public AbstractList$ListIteratorImpl(AbstractList var1, int var2) {
      super(var1);
      this.this$0 = var1;
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var2, var1.size());
      this.setIndex(var2);
   }

   public void add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void set(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
