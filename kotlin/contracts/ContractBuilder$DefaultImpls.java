package kotlin.contracts;

import kotlin.Function;
import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 3
)
public final class ContractBuilder$DefaultImpls {
   public static CallsInPlace callsInPlace$default(ContractBuilder var0, Function var1, InvocationKind var2, int var3, Object var4) {
      if (var4 != null) {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: callsInPlace");
      } else {
         if ((var3 & 2) != 0) {
            var2 = InvocationKind.UNKNOWN;
         }

         return var0.callsInPlace(var1, var2);
      }
   }
}
