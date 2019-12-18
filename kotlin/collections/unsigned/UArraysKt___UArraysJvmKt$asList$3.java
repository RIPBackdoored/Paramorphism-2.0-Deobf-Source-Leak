package kotlin.collections.unsigned;

import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u001b\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\rJ\u0014\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0006H\u0096\u0002ø\u0001\u0000J\u001a\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\b\u0010\u0013\u001a\u00020\nH\u0016J\u001a\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0002H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0012R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"},
   d2 = {"kotlin/collections/unsigned/UArraysKt___UArraysJvmKt$asList$3", "Lkotlin/collections/AbstractList;", "Lkotlin/UByte;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "", "element", "contains-7apg3OU", "(B)Z", "get", "index", "indexOf", "indexOf-7apg3OU", "(B)I", "isEmpty", "lastIndexOf", "lastIndexOf-7apg3OU", "kotlin-stdlib"}
)
public final class UArraysKt___UArraysJvmKt$asList$3 extends AbstractList implements RandomAccess {
   final byte[] $this_asList;

   public int getSize() {
      return UByteArray.getSize-impl(this.$this_asList);
   }

   public boolean isEmpty() {
      return UByteArray.isEmpty-impl(this.$this_asList);
   }

   public boolean contains_7apg3OU/* $FF was: contains-7apg3OU*/(byte var1) {
      return UByteArray.contains-7apg3OU(this.$this_asList, var1);
   }

   public final boolean contains(Object var1) {
      return var1 instanceof UByte ? this.contains-7apg3OU(((UByte)var1).unbox-impl()) : false;
   }

   @NotNull
   public UByte get(int var1) {
      return UByte.box-impl(UByteArray.get-impl(this.$this_asList, var1));
   }

   public Object get(int var1) {
      return this.get(var1);
   }

   public int indexOf_7apg3OU/* $FF was: indexOf-7apg3OU*/(byte var1) {
      byte[] var2 = this.$this_asList;
      boolean var3 = false;
      boolean var6 = false;
      return ArraysKt.indexOf(var2, var1);
   }

   public final int indexOf(Object var1) {
      return var1 instanceof UByte ? this.indexOf-7apg3OU(((UByte)var1).unbox-impl()) : -1;
   }

   public int lastIndexOf_7apg3OU/* $FF was: lastIndexOf-7apg3OU*/(byte var1) {
      byte[] var2 = this.$this_asList;
      boolean var3 = false;
      boolean var6 = false;
      return ArraysKt.lastIndexOf(var2, var1);
   }

   public final int lastIndexOf(Object var1) {
      return var1 instanceof UByte ? this.lastIndexOf-7apg3OU(((UByte)var1).unbox-impl()) : -1;
   }

   UArraysKt___UArraysJvmKt$asList$3(byte[] var1) {
      super();
      this.$this_asList = var1;
   }
}
