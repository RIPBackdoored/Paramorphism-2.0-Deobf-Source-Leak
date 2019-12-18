package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0081\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/coroutines/intrinsics/CoroutineSingletons;", "", "(Ljava/lang/String;I)V", "COROUTINE_SUSPENDED", "UNDECIDED", "RESUMED", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@PublishedApi
public final class CoroutineSingletons extends Enum {
   public static final CoroutineSingletons COROUTINE_SUSPENDED;
   public static final CoroutineSingletons UNDECIDED;
   public static final CoroutineSingletons RESUMED;
   private static final CoroutineSingletons[] $VALUES = new CoroutineSingletons[]{COROUTINE_SUSPENDED = new CoroutineSingletons("COROUTINE_SUSPENDED", 0), UNDECIDED = new CoroutineSingletons("UNDECIDED", 1), RESUMED = new CoroutineSingletons("RESUMED", 2)};

   private CoroutineSingletons(String var1, int var2) {
      super(var1, var2);
   }

   public static CoroutineSingletons[] values() {
      return (CoroutineSingletons[])$VALUES.clone();
   }

   public static CoroutineSingletons valueOf(String var0) {
      return (CoroutineSingletons)Enum.valueOf(CoroutineSingletons.class, var0);
   }
}
