package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000!\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\t\u0010\u0013\u001a\u00020\u0014H\u0096\u0002J\u000e\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001e\u0010\u000b\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0016"},
   d2 = {"kotlin/sequences/DropWhileSequence$iterator$1", "", "dropState", "", "getDropState", "()I", "setDropState", "(I)V", "iterator", "getIterator", "()Ljava/util/Iterator;", "nextItem", "getNextItem", "()Ljava/lang/Object;", "setNextItem", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "drop", "", "hasNext", "", "next", "kotlin-stdlib"}
)
public final class DropWhileSequence$iterator$1 implements Iterator, KMappedMarker {
   @NotNull
   private final Iterator iterator;
   private int dropState;
   @Nullable
   private Object nextItem;
   final DropWhileSequence this$0;

   @NotNull
   public final Iterator getIterator() {
      return this.iterator;
   }

   public final int getDropState() {
      return this.dropState;
   }

   public final void setDropState(int var1) {
      this.dropState = var1;
   }

   @Nullable
   public final Object getNextItem() {
      return this.nextItem;
   }

   public final void setNextItem(@Nullable Object var1) {
      this.nextItem = var1;
   }

   private final void drop() {
      while(true) {
         if (this.iterator.hasNext()) {
            Object var1 = this.iterator.next();
            if ((Boolean)DropWhileSequence.access$getPredicate$p(this.this$0).invoke(var1)) {
               continue;
            }

            this.nextItem = var1;
            this.dropState = 1;
            return;
         }

         this.dropState = 0;
         return;
      }
   }

   public Object next() {
      if (this.dropState == -1) {
         this.drop();
      }

      if (this.dropState == 1) {
         Object var1 = this.nextItem;
         this.nextItem = null;
         this.dropState = 0;
         return var1;
      } else {
         return this.iterator.next();
      }
   }

   public boolean hasNext() {
      if (this.dropState == -1) {
         this.drop();
      }

      return this.dropState == 1 || this.iterator.hasNext();
   }

   DropWhileSequence$iterator$1(DropWhileSequence var1) {
      super();
      this.this$0 = var1;
      this.iterator = DropWhileSequence.access$getSequence$p(var1).iterator();
      this.dropState = -1;
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
