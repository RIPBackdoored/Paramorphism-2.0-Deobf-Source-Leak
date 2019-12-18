package kotlin.collections.unsigned;

import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u001b\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\rJ\u0014\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0006H\u0096\u0002ø\u0001\u0000J\u001a\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\b\u0010\u0013\u001a\u00020\nH\u0016J\u001a\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0012R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"},
   d2 = {"kotlin/collections/unsigned/UArraysKt___UArraysJvmKt$asList$1", "Lkotlin/collections/AbstractList;", "Lkotlin/UInt;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "contains-WZ4Q5Ns", "(I)Z", "get", "index", "indexOf", "indexOf-WZ4Q5Ns", "(I)I", "isEmpty", "lastIndexOf", "lastIndexOf-WZ4Q5Ns", "kotlin-stdlib"}
)
public final class UArraysKt___UArraysJvmKt$asList$1 extends AbstractList implements RandomAccess {
   final int[] $this_asList;

   public int getSize() {
      return UIntArray.getSize-impl(this.$this_asList);
   }

   public boolean isEmpty() {
      return UIntArray.isEmpty-impl(this.$this_asList);
   }

   public boolean contains_WZ4Q5Ns/* $FF was: contains-WZ4Q5Ns*/(int var1) {
      return UIntArray.contains-WZ4Q5Ns(this.$this_asList, var1);
   }

   public final boolean contains(Object var1) {
      return var1 instanceof UInt ? this.contains-WZ4Q5Ns(((UInt)var1).unbox-impl()) : false;
   }

   @NotNull
   public UInt get(int var1) {
      return UInt.box-impl(UIntArray.get-impl(this.$this_asList, var1));
   }

   public Object get(int var1) {
      return this.get(var1);
   }

   public int indexOf_WZ4Q5Ns/* $FF was: indexOf-WZ4Q5Ns*/(int var1) {
      int[] var2 = this.$this_asList;
      boolean var3 = false;
      boolean var6 = false;
      return ArraysKt.indexOf(var2, var1);
   }

   public final int indexOf(Object var1) {
      return var1 instanceof UInt ? this.indexOf-WZ4Q5Ns(((UInt)var1).unbox-impl()) : -1;
   }

   public int lastIndexOf_WZ4Q5Ns/* $FF was: lastIndexOf-WZ4Q5Ns*/(int var1) {
      int[] var2 = this.$this_asList;
      boolean var3 = false;
      boolean var6 = false;
      return ArraysKt.lastIndexOf(var2, var1);
   }

   public final int lastIndexOf(Object var1) {
      return var1 instanceof UInt ? this.lastIndexOf-WZ4Q5Ns(((UInt)var1).unbox-impl()) : -1;
   }

   UArraysKt___UArraysJvmKt$asList$1(int[] var1) {
      super();
      this.$this_asList = var1;
   }
}
