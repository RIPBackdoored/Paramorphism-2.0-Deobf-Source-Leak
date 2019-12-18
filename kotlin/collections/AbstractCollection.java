package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b'\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0007\b\u0004¢\u0006\u0002\u0010\u0003J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\u0016J\b\u0010\u000e\u001a\u00020\tH\u0016J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H¦\u0002J\u0015\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0012H\u0014¢\u0006\u0002\u0010\u0014J'\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0012\"\u0004\b\u0001\u0010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0012H\u0014¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001a"},
   d2 = {"Lkotlin/collections/AbstractCollection;", "E", "", "()V", "size", "", "getSize", "()I", "contains", "", "element", "(Ljava/lang/Object;)Z", "containsAll", "elements", "isEmpty", "iterator", "", "toArray", "", "", "()[Ljava/lang/Object;", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public abstract class AbstractCollection implements Collection, KMappedMarker {
   public abstract int getSize();

   public final int size() {
      return this.getSize();
   }

   @NotNull
   public abstract Iterator iterator();

   public boolean contains(Object var1) {
      boolean var3 = false;
      boolean var10000;
      if (this instanceof Collection && ((Collection)this).isEmpty()) {
         var10000 = false;
      } else {
         Iterator var4 = this.iterator();

         while(true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            Object var5 = var4.next();
            boolean var7 = false;
            if (Intrinsics.areEqual(var5, var1)) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   public boolean containsAll(@NotNull Collection var1) {
      Iterable var2 = (Iterable)var1;
      boolean var3 = false;
      boolean var10000;
      if (((Collection)var2).isEmpty()) {
         var10000 = true;
      } else {
         Iterator var4 = var2.iterator();

         while(true) {
            if (!var4.hasNext()) {
               var10000 = true;
               break;
            }

            Object var5 = var4.next();
            boolean var7 = false;
            if (!this.contains(var5)) {
               var10000 = false;
               break;
            }
         }
      }

      return var10000;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   @NotNull
   public String toString() {
      return CollectionsKt.joinToString$default(this, (CharSequence)", ", (CharSequence)"[", (CharSequence)"]", 0, (CharSequence)null, (Function1)(new AbstractCollection$toString$1(this)), 24, (Object)null);
   }

   @NotNull
   public Object[] toArray() {
      Collection var1 = (Collection)this;
      boolean var2 = false;
      return CollectionToArray.toArray(var1);
   }

   @NotNull
   public Object[] toArray(@NotNull Object[] var1) {
      Collection var2 = (Collection)this;
      boolean var3 = false;
      return CollectionToArray.toArray(var2, var1);
   }

   protected AbstractCollection() {
      super();
   }

   public boolean add(Object var1) {
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

   public boolean retainAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
