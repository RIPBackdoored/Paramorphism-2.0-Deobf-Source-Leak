package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010*\n\u0002\b\b\b'\u0018\u0000 \u001c*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0004\u001c\u001d\u001e\u001fB\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\u0016\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u0006H¦\u0002¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0006H\u0016J\u0015\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0096\u0002J\u0015\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00182\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0016R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006 "},
   d2 = {"Lkotlin/collections/AbstractList;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "size", "", "getSize", "()I", "equals", "", "other", "", "get", "index", "(I)Ljava/lang/Object;", "hashCode", "indexOf", "element", "(Ljava/lang/Object;)I", "iterator", "", "lastIndexOf", "listIterator", "", "subList", "fromIndex", "toIndex", "Companion", "IteratorImpl", "ListIteratorImpl", "SubList", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public abstract class AbstractList extends AbstractCollection implements List, KMappedMarker {
   public static final AbstractList$Companion Companion = new AbstractList$Companion((DefaultConstructorMarker)null);

   public abstract int getSize();

   public abstract Object get(int var1);

   @NotNull
   public Iterator iterator() {
      return (Iterator)(new AbstractList$IteratorImpl(this));
   }

   public int indexOf(Object var1) {
      boolean var3 = false;
      int var4 = 0;
      Iterator var5 = this.iterator();

      int var10000;
      while(true) {
         if (!var5.hasNext()) {
            var10000 = -1;
            break;
         }

         Object var6 = var5.next();
         boolean var8 = false;
         if (Intrinsics.areEqual(var6, var1)) {
            var10000 = var4;
            break;
         }

         ++var4;
      }

      return var10000;
   }

   public int lastIndexOf(Object var1) {
      boolean var3 = false;
      ListIterator var4 = this.listIterator(this.size());

      int var10000;
      while(true) {
         if (var4.hasPrevious()) {
            Object var5 = var4.previous();
            boolean var6 = false;
            if (!Intrinsics.areEqual(var5, var1)) {
               continue;
            }

            var10000 = var4.nextIndex();
            break;
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   @NotNull
   public ListIterator listIterator() {
      return (ListIterator)(new AbstractList$ListIteratorImpl(this, 0));
   }

   @NotNull
   public ListIterator listIterator(int var1) {
      return (ListIterator)(new AbstractList$ListIteratorImpl(this, var1));
   }

   @NotNull
   public List subList(int var1, int var2) {
      return (List)(new AbstractList$SubList(this, var1, var2));
   }

   public boolean equals(@Nullable Object var1) {
      if (var1 == (AbstractList)this) {
         return true;
      } else {
         return !(var1 instanceof List) ? false : Companion.orderedEquals$kotlin_stdlib((Collection)this, (Collection)var1);
      }
   }

   public int hashCode() {
      return Companion.orderedHashCode$kotlin_stdlib((Collection)this);
   }

   protected AbstractList() {
      super();
   }

   public void add(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(int var1, Collection var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object set(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
