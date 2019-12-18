package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000#\n\u0000\n\u0002\u0010(\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\u000e\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004R\u001e\u0010\u0005\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\n\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0016"},
   d2 = {"kotlin/sequences/TakeWhileSequence$iterator$1", "", "iterator", "getIterator", "()Ljava/util/Iterator;", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "nextState", "", "getNextState", "()I", "setNextState", "(I)V", "calcNext", "", "hasNext", "", "next", "kotlin-stdlib"}
)
public final class TakeWhileSequence$iterator$1 implements Iterator, KMappedMarker {
   @NotNull
   private final Iterator iterator;
   private int nextState;
   @Nullable
   private Object nextItem;
   final TakeWhileSequence this$0;

   @NotNull
   public final Iterator getIterator() {
      return this.iterator;
   }

   public final int getNextState() {
      return this.nextState;
   }

   public final void setNextState(int var1) {
      this.nextState = var1;
   }

   @Nullable
   public final Object getNextItem() {
      return this.nextItem;
   }

   public final void setNextItem(@Nullable Object var1) {
      this.nextItem = var1;
   }

   private final void calcNext() {
      if (this.iterator.hasNext()) {
         Object var1 = this.iterator.next();
         if ((Boolean)TakeWhileSequence.access$getPredicate$p(this.this$0).invoke(var1)) {
            this.nextState = 1;
            this.nextItem = var1;
            return;
         }
      }

      this.nextState = 0;
   }

   public Object next() {
      if (this.nextState == -1) {
         this.calcNext();
      }

      if (this.nextState == 0) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         Object var1 = this.nextItem;
         this.nextItem = null;
         this.nextState = -1;
         return var1;
      }
   }

   public boolean hasNext() {
      if (this.nextState == -1) {
         this.calcNext();
      }

      return this.nextState == 1;
   }

   TakeWhileSequence$iterator$1(TakeWhileSequence var1) {
      super();
      this.this$0 = var1;
      this.iterator = TakeWhileSequence.access$getSequence$p(var1).iterator();
      this.nextState = -1;
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
