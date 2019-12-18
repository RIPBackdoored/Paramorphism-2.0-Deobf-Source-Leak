package kotlin.collections;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"},
   d2 = {"<anonymous>", "Lkotlin/collections/ByteIterator;", "invoke"}
)
final class ArraysKt___ArraysKt$withIndex$2 extends Lambda implements Function0 {
   final byte[] $this_withIndex;

   public Object invoke() {
      return this.invoke();
   }

   @NotNull
   public final ByteIterator invoke() {
      return ArrayIteratorsKt.iterator(this.$this_withIndex);
   }

   ArraysKt___ArraysKt$withIndex$2(byte[] var1) {
      super(0);
      this.$this_withIndex = var1;
   }
}
