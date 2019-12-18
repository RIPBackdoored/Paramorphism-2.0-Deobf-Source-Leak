package kotlin.contracts;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.ContractsDsl;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/contracts/InvocationKind;", "", "(Ljava/lang/String;I)V", "AT_MOST_ONCE", "AT_LEAST_ONCE", "EXACTLY_ONCE", "UNKNOWN", "kotlin-stdlib"}
)
@ContractsDsl
@ExperimentalContracts
@SinceKotlin(
   version = "1.3"
)
public final class InvocationKind extends Enum {
   @ContractsDsl
   public static final InvocationKind AT_MOST_ONCE;
   @ContractsDsl
   public static final InvocationKind AT_LEAST_ONCE;
   @ContractsDsl
   public static final InvocationKind EXACTLY_ONCE;
   @ContractsDsl
   public static final InvocationKind UNKNOWN;
   private static final InvocationKind[] $VALUES = new InvocationKind[]{AT_MOST_ONCE = new InvocationKind("AT_MOST_ONCE", 0), AT_LEAST_ONCE = new InvocationKind("AT_LEAST_ONCE", 1), EXACTLY_ONCE = new InvocationKind("EXACTLY_ONCE", 2), UNKNOWN = new InvocationKind("UNKNOWN", 3)};

   private InvocationKind(String var1, int var2) {
      super(var1, var2);
   }

   public static InvocationKind[] values() {
      return (InvocationKind[])$VALUES.clone();
   }

   public static InvocationKind valueOf(String var0) {
      return (InvocationKind)Enum.valueOf(InvocationKind.class, var0);
   }
}
