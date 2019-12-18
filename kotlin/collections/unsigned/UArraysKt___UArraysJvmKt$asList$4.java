package kotlin.collections.unsigned;

import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u001b\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\rJ\u0014\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0006H\u0096\u0002ø\u0001\u0000J\u001a\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\b\u0010\u0013\u001a\u00020\nH\u0016J\u001a\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0012R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"},
   d2 = {"kotlin/collections/unsigned/UArraysKt___UArraysJvmKt$asList$4", "Lkotlin/collections/AbstractList;", "Lkotlin/UShort;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "contains-xj2QHRw", "(S)Z", "get", "index", "indexOf", "indexOf-xj2QHRw", "(S)I", "isEmpty", "lastIndexOf", "lastIndexOf-xj2QHRw", "kotlin-stdlib"}
)
public final class UArraysKt___UArraysJvmKt$asList$4 extends AbstractList implements RandomAccess {
   final short[] $this_asList;

   public int getSize() {
      return UShortArray.getSize-impl(this.$this_asList);
   }

   public boolean isEmpty() {
      return UShortArray.isEmpty-impl(this.$this_asList);
   }

   public boolean contains_xj2QHRw/* $FF was: contains-xj2QHRw*/(short var1) {
      return UShortArray.contains-xj2QHRw(this.$this_asList, var1);
   }

   public final boolean contains(Object var1) {
      return var1 instanceof UShort ? this.contains-xj2QHRw(((UShort)var1).unbox-impl()) : false;
   }

   @NotNull
   public UShort get(int var1) {
      return UShort.box-impl(UShortArray.get-impl(this.$this_asList, var1));
   }

   public Object get(int var1) {
      return this.get(var1);
   }

   public int indexOf_xj2QHRw/* $FF was: indexOf-xj2QHRw*/(short var1) {
      short[] var2 = this.$this_asList;
      boolean var3 = false;
      boolean var6 = false;
      return ArraysKt.indexOf(var2, var1);
   }

   public final int indexOf(Object var1) {
      return var1 instanceof UShort ? this.indexOf-xj2QHRw(((UShort)var1).unbox-impl()) : -1;
   }

   public int lastIndexOf_xj2QHRw/* $FF was: lastIndexOf-xj2QHRw*/(short var1) {
      short[] var2 = this.$this_asList;
      boolean var3 = false;
      boolean var6 = false;
      return ArraysKt.lastIndexOf(var2, var1);
   }

   public final int lastIndexOf(Object var1) {
      return var1 instanceof UShort ? this.lastIndexOf-xj2QHRw(((UShort)var1).unbox-impl()) : -1;
   }

   UArraysKt___UArraysJvmKt$asList$4(short[] var1) {
      super();
      this.$this_asList = var1;
   }
}
