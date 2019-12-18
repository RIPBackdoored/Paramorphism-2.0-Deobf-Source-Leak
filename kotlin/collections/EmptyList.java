package kotlin.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010*\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u00042\u00060\u0005j\u0002`\u0006B\u0007\b\u0002¢\u0006\u0002\u0010\u0007J\u0011\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\u0011\u001a\u00020\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\u0013H\u0016J\u0013\u0010\u0014\u001a\u00020\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\u0011\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u000bH\u0096\u0002J\b\u0010\u0019\u001a\u00020\u000bH\u0016J\u0010\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0016J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0096\u0002J\u0010\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020 H\u0016J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020 2\u0006\u0010\u0018\u001a\u00020\u000bH\u0016J\b\u0010!\u001a\u00020\u0016H\u0002J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u000bH\u0016J\b\u0010%\u001a\u00020&H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006'"},
   d2 = {"Lkotlin/collections/EmptyList;", "", "", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "()V", "serialVersionUID", "", "size", "", "getSize", "()I", "contains", "", "element", "containsAll", "elements", "", "equals", "other", "", "get", "index", "hashCode", "indexOf", "isEmpty", "iterator", "", "lastIndexOf", "listIterator", "", "readResolve", "subList", "fromIndex", "toIndex", "toString", "", "kotlin-stdlib"}
)
public final class EmptyList implements List, Serializable, RandomAccess, KMappedMarker {
   private static final long serialVersionUID = -7390468764508069838L;
   public static final EmptyList INSTANCE;

   public boolean equals(@Nullable Object var1) {
      return var1 instanceof List && ((List)var1).isEmpty();
   }

   public int hashCode() {
      return 1;
   }

   @NotNull
   public String toString() {
      return "[]";
   }

   public int getSize() {
      return 0;
   }

   public final int size() {
      return this.getSize();
   }

   public boolean isEmpty() {
      return true;
   }

   public boolean contains(@NotNull Void var1) {
      return false;
   }

   public final boolean contains(Object var1) {
      return var1 instanceof Void ? this.contains((Void)var1) : false;
   }

   public boolean containsAll(@NotNull Collection var1) {
      return var1.isEmpty();
   }

   @NotNull
   public Void get(int var1) {
      throw (Throwable)(new IndexOutOfBoundsException("Empty list doesn't contain element at index " + var1 + '.'));
   }

   public Object get(int var1) {
      return this.get(var1);
   }

   public int indexOf(@NotNull Void var1) {
      return -1;
   }

   public final int indexOf(Object var1) {
      return var1 instanceof Void ? this.indexOf((Void)var1) : -1;
   }

   public int lastIndexOf(@NotNull Void var1) {
      return -1;
   }

   public final int lastIndexOf(Object var1) {
      return var1 instanceof Void ? this.lastIndexOf((Void)var1) : -1;
   }

   @NotNull
   public Iterator iterator() {
      return (Iterator)EmptyIterator.INSTANCE;
   }

   @NotNull
   public ListIterator listIterator() {
      return (ListIterator)EmptyIterator.INSTANCE;
   }

   @NotNull
   public ListIterator listIterator(int var1) {
      if (var1 != 0) {
         throw (Throwable)(new IndexOutOfBoundsException("Index: " + var1));
      } else {
         return (ListIterator)EmptyIterator.INSTANCE;
      }
   }

   @NotNull
   public List subList(int var1, int var2) {
      if (var1 == 0 && var2 == 0) {
         return (List)this;
      } else {
         throw (Throwable)(new IndexOutOfBoundsException("fromIndex: " + var1 + ", toIndex: " + var2));
      }
   }

   private final Object readResolve() {
      return INSTANCE;
   }

   private EmptyList() {
      super();
   }

   static {
      EmptyList var0 = new EmptyList();
      INSTANCE = var0;
   }

   public boolean add(Void var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void add(int var1, Void var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(int var1, Collection var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean removeAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Void remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean retainAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Void set(int var1, Void var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void add(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object set(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   public Object[] toArray(Object[] var1) {
      return CollectionToArray.toArray(this, var1);
   }
}
