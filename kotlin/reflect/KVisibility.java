package kotlin.reflect;

import kotlin.Metadata;
import kotlin.SinceKotlin;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/reflect/KVisibility;", "", "(Ljava/lang/String;I)V", "PUBLIC", "PROTECTED", "INTERNAL", "PRIVATE", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public final class KVisibility extends Enum {
   public static final KVisibility PUBLIC;
   public static final KVisibility PROTECTED;
   public static final KVisibility INTERNAL;
   public static final KVisibility PRIVATE;
   private static final KVisibility[] $VALUES = new KVisibility[]{PUBLIC = new KVisibility("PUBLIC", 0), PROTECTED = new KVisibility("PROTECTED", 1), INTERNAL = new KVisibility("INTERNAL", 2), PRIVATE = new KVisibility("PRIVATE", 3)};

   private KVisibility(String var1, int var2) {
      super(var1, var2);
   }

   public static KVisibility[] values() {
      return (KVisibility[])$VALUES.clone();
   }

   public static KVisibility valueOf(String var0) {
      return (KVisibility)Enum.valueOf(KVisibility.class, var0);
   }
}
