package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000#\n\u0000\n\u0002\u0010(\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u000b\u001a\u00020\fH\u0002J\t\u0010\r\u001a\u00020\u000eH\u0096\u0002J\u000e\u0010\u000f\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0011"},
   d2 = {"kotlin/sequences/SubSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "position", "", "getPosition", "()I", "setPosition", "(I)V", "drop", "", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}
)
public final class SubSequence$iterator$1 implements Iterator, KMappedMarker {
   @NotNull
   private final Iterator iterator;
   private int position;
   final SubSequence this$0;

   @NotNull
   public final Iterator getIterator() {
      return this.iterator;
   }

   public final int getPosition() {
      return this.position;
   }

   public final void setPosition(int var1) {
      this.position = var1;
   }

   private final void drop() {
      while(this.position < SubSequence.access$getStartIndex$p(this.this$0) && this.iterator.hasNext()) {
         this.iterator.next();
         int var10001 = this.position++;
      }

   }

   public boolean hasNext() {
      this.drop();
      return this.position < SubSequence.access$getEndIndex$p(this.this$0) && this.iterator.hasNext();
   }

   public Object next() {
      this.drop();
      if (this.position >= SubSequence.access$getEndIndex$p(this.this$0)) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         int var10001 = this.position++;
         return this.iterator.next();
      }
   }

   SubSequence$iterator$1(SubSequence var1) {
      super();
      this.this$0 = var1;
      this.iterator = SubSequence.access$getSequence$p(var1).iterator();
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
