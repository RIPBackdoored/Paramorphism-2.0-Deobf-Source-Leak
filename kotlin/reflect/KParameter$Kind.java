package kotlin.reflect;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/reflect/KParameter$Kind;", "", "(Ljava/lang/String;I)V", "INSTANCE", "EXTENSION_RECEIVER", "VALUE", "kotlin-stdlib"}
)
public final class KParameter$Kind extends Enum {
   public static final KParameter$Kind INSTANCE;
   public static final KParameter$Kind EXTENSION_RECEIVER;
   public static final KParameter$Kind VALUE;
   private static final KParameter$Kind[] $VALUES = new KParameter$Kind[]{INSTANCE = new KParameter$Kind("INSTANCE", 0), EXTENSION_RECEIVER = new KParameter$Kind("EXTENSION_RECEIVER", 1), VALUE = new KParameter$Kind("VALUE", 2)};

   private KParameter$Kind(String var1, int var2) {
      super(var1, var2);
   }

   public static KParameter$Kind[] values() {
      return (KParameter$Kind[])$VALUES.clone();
   }

   public static KParameter$Kind valueOf(String var0) {
      return (KParameter$Kind)Enum.valueOf(KParameter$Kind.class, var0);
   }
}
