package kotlin.collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010*\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\t\u0010\u0004\u001a\u00020\u0005H\u0096\u0002J\b\u0010\u0006\u001a\u00020\u0005H\u0016J\t\u0010\u0007\u001a\u00020\u0002H\u0096\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0002H\u0016J\b\u0010\u000b\u001a\u00020\tH\u0016¨\u0006\f"},
   d2 = {"Lkotlin/collections/EmptyIterator;", "", "", "()V", "hasNext", "", "hasPrevious", "next", "nextIndex", "", "previous", "previousIndex", "kotlin-stdlib"}
)
public final class EmptyIterator implements ListIterator, KMappedMarker {
   public static final EmptyIterator INSTANCE;

   public boolean hasNext() {
      return false;
   }

   public boolean hasPrevious() {
      return false;
   }

   public int nextIndex() {
      return 0;
   }

   public int previousIndex() {
      return -1;
   }

   @NotNull
   public Void next() {
      throw (Throwable)(new NoSuchElementException());
   }

   public Object next() {
      return this.next();
   }

   @NotNull
   public Void previous() {
      throw (Throwable)(new NoSuchElementException());
   }

   public Object previous() {
      return this.previous();
   }

   private EmptyIterator() {
      super();
   }

   static {
      EmptyIterator var0 = new EmptyIterator();
      INSTANCE = var0;
   }

   public void add(Void var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void set(Void var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void set(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
