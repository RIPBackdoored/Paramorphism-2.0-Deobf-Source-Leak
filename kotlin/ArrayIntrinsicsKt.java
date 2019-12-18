package kotlin;

import kotlin.jvm.internal.Intrinsics;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000b\b\u0000\u0010\u0002\u0018\u0001¢\u0006\u0002\b\u0003H\u0086\b¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
   d2 = {"emptyArray", "", "T", "Lkotlin/internal/PureReifiable;", "()[Ljava/lang/Object;", "kotlin-stdlib"}
)
public final class ArrayIntrinsicsKt {
   private static final Object[] emptyArray() {
      byte var0 = 0;
      Intrinsics.reifiedOperationMarker(0, "T?");
      return new Object[0];
   }
}
