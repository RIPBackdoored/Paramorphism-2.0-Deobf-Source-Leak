package kotlin.jvm.internal;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0001\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0002¨\u0006\u0002"},
   d2 = {"notSupportedError", "", "kotlin-stdlib"}
)
public final class LocalVariableReferencesKt {
   private static final Void notSupportedError() {
      throw (Throwable)(new UnsupportedOperationException("Not supported for local property reference."));
   }

   public static final Void access$notSupportedError() {
      return notSupportedError();
   }
}
