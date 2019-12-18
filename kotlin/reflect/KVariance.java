package kotlin.reflect;

import kotlin.Metadata;
import kotlin.SinceKotlin;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/reflect/KVariance;", "", "(Ljava/lang/String;I)V", "INVARIANT", "IN", "OUT", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public final class KVariance extends Enum {
   public static final KVariance INVARIANT;
   public static final KVariance IN;
   public static final KVariance OUT;
   private static final KVariance[] $VALUES = new KVariance[]{INVARIANT = new KVariance("INVARIANT", 0), IN = new KVariance("IN", 1), OUT = new KVariance("OUT", 2)};

   private KVariance(String var1, int var2) {
      super(var1, var2);
   }

   public static KVariance[] values() {
      return (KVariance[])$VALUES.clone();
   }

   public static KVariance valueOf(String var0) {
      return (KVariance)Enum.valueOf(KVariance.class, var0);
   }
}
