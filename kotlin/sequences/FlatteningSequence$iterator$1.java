package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0015\n\u0000\n\u0002\u0010(\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\t\u001a\u00020\nH\u0002J\t\u0010\u000b\u001a\u00020\nH\u0096\u0002J\u000e\u0010\f\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\rR\"\u0010\u0002\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0004¨\u0006\u000e"},
   d2 = {"kotlin/sequences/FlatteningSequence$iterator$1", "", "itemIterator", "getItemIterator", "()Ljava/util/Iterator;", "setItemIterator", "(Ljava/util/Iterator;)V", "iterator", "getIterator", "ensureItemIterator", "", "hasNext", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}
)
public final class FlatteningSequence$iterator$1 implements Iterator, KMappedMarker {
   @NotNull
   private final Iterator iterator;
   @Nullable
   private Iterator itemIterator;
   final FlatteningSequence this$0;

   @NotNull
   public final Iterator getIterator() {
      return this.iterator;
   }

   @Nullable
   public final Iterator getItemIterator() {
      return this.itemIterator;
   }

   public final void setItemIterator(@Nullable Iterator var1) {
      this.itemIterator = var1;
   }

   public Object next() {
      if (!this.ensureItemIterator()) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         Iterator var10000 = this.itemIterator;
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         return var10000.next();
      }
   }

   public boolean hasNext() {
      return this.ensureItemIterator();
   }

   private final boolean ensureItemIterator() {
      Iterator var10000 = this.itemIterator;
      if (var10000 != null) {
         if (!var10000.hasNext()) {
            this.itemIterator = (Iterator)null;
         }
      }

      Iterator var2;
      do {
         if (this.itemIterator != null) {
            return true;
         }

         if (!this.iterator.hasNext()) {
            return false;
         }

         Object var1 = this.iterator.next();
         var2 = (Iterator)FlatteningSequence.access$getIterator$p(this.this$0).invoke(FlatteningSequence.access$getTransformer$p(this.this$0).invoke(var1));
      } while(!var2.hasNext());

      this.itemIterator = var2;
      return true;
   }

   FlatteningSequence$iterator$1(FlatteningSequence var1) {
      super();
      this.this$0 = var1;
      this.iterator = FlatteningSequence.access$getSequence$p(var1).iterator();
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
