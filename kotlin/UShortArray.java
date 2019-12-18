package kotlin;

import java.util.Arrays;
import java.util.Collection;
import kotlin.collections.ArraysKt;
import kotlin.collections.UShortIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0017\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\bø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004HÖ\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016¢\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002¢\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,HÖ\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006."},
   d2 = {"Lkotlin/UShortArray;", "", "Lkotlin/UShort;", "size", "", "constructor-impl", "(I)[S", "storage", "", "([S)[S", "getSize-impl", "([S)I", "storage$annotations", "()V", "contains", "", "element", "contains-xj2QHRw", "([SS)Z", "containsAll", "elements", "containsAll-impl", "([SLjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([SI)S", "hashCode", "isEmpty", "isEmpty-impl", "([S)Z", "iterator", "Lkotlin/collections/UShortIterator;", "iterator-impl", "([S)Lkotlin/collections/UShortIterator;", "set", "", "value", "set-01HTLdE", "([SIS)V", "toString", "", "Iterator", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class UShortArray implements Collection, KMappedMarker {
   @NotNull
   private final short[] storage;

   public int getSize() {
      return getSize-impl(this.storage);
   }

   public final int size() {
      return this.getSize();
   }

   @NotNull
   public UShortIterator iterator() {
      return iterator-impl(this.storage);
   }

   public java.util.Iterator iterator() {
      return (java.util.Iterator)this.iterator();
   }

   public boolean contains_xj2QHRw/* $FF was: contains-xj2QHRw*/(short var1) {
      return contains-xj2QHRw(this.storage, var1);
   }

   public final boolean contains(Object var1) {
      return var1 instanceof UShort ? this.contains-xj2QHRw(((UShort)var1).unbox-impl()) : false;
   }

   public boolean containsAll(@NotNull Collection var1) {
      return containsAll-impl(this.storage, var1);
   }

   public boolean isEmpty() {
      return isEmpty-impl(this.storage);
   }

   /** @deprecated */
   @PublishedApi
   public static void storage$annotations() {
   }

   @PublishedApi
   private UShortArray(@NotNull short[] var1) {
      super();
      this.storage = var1;
   }

   public static final short get_impl/* $FF was: get-impl*/(short[] var0, int var1) {
      short var2 = var0[var1];
      boolean var3 = false;
      return UShort.constructor-impl(var2);
   }

   public static final void set_01HTLdE/* $FF was: set-01HTLdE*/(short[] var0, int var1, short var2) {
      boolean var4 = false;
      var0[var1] = var2;
   }

   public static int getSize_impl/* $FF was: getSize-impl*/(short[] var0) {
      return var0.length;
   }

   @NotNull
   public static UShortIterator iterator_impl/* $FF was: iterator-impl*/(short[] var0) {
      return (UShortIterator)(new UShortArray$Iterator(var0));
   }

   public static boolean contains_xj2QHRw/* $FF was: contains-xj2QHRw*/(short[] var0, short var1) {
      boolean var3 = false;
      return ArraysKt.contains(var0, var1);
   }

   public static boolean containsAll_impl/* $FF was: containsAll-impl*/(short[] var0, @NotNull Collection var1) {
      Iterable var2 = (Iterable)var1;
      boolean var3 = false;
      boolean var10000;
      if (((Collection)var2).isEmpty()) {
         var10000 = true;
      } else {
         java.util.Iterator var4 = var2.iterator();

         while(true) {
            if (!var4.hasNext()) {
               var10000 = true;
               break;
            }

            label22: {
               Object var5 = var4.next();
               boolean var7 = false;
               if (var5 instanceof UShort) {
                  short var8 = ((UShort)var5).unbox-impl();
                  boolean var10 = false;
                  if (ArraysKt.contains(var0, var8)) {
                     var10000 = true;
                     break label22;
                  }
               }

               var10000 = false;
            }

            if (!var10000) {
               var10000 = false;
               break;
            }
         }
      }

      return var10000;
   }

   public static boolean isEmpty_impl/* $FF was: isEmpty-impl*/(short[] var0) {
      return var0.length == 0;
   }

   @PublishedApi
   @NotNull
   public static short[] constructor_impl/* $FF was: constructor-impl*/(@NotNull short[] var0) {
      return var0;
   }

   @NotNull
   public static short[] constructor_impl/* $FF was: constructor-impl*/(int var0) {
      return constructor-impl(new short[var0]);
   }

   @NotNull
   public static final UShortArray box_impl/* $FF was: box-impl*/(@NotNull short[] var0) {
      return new UShortArray(var0);
   }

   @NotNull
   public static String toString_impl/* $FF was: toString-impl*/(short[] var0) {
      return "UShortArray(storage=" + Arrays.toString(var0) + ")";
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(short[] var0) {
      return var0 != null ? Arrays.hashCode(var0) : 0;
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(short[] var0, @Nullable Object var1) {
      if (var1 instanceof UShortArray) {
         short[] var2 = ((UShortArray)var1).unbox-impl();
         if (Intrinsics.areEqual((Object)var0, (Object)var2)) {
            return true;
         }
      }

      return false;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(@NotNull short[] var0, @NotNull short[] var1) {
      throw null;
   }

   @NotNull
   public final short[] unbox_impl/* $FF was: unbox-impl*/() {
      return this.storage;
   }

   public String toString() {
      return toString-impl(this.storage);
   }

   public int hashCode() {
      return hashCode-impl(this.storage);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.storage, var1);
   }

   public boolean add_xj2QHRw/* $FF was: add-xj2QHRw*/(short var1) {
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

   public boolean add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   public Object[] toArray(Object[] var1) {
      return CollectionToArray.toArray(this, var1);
   }
}
