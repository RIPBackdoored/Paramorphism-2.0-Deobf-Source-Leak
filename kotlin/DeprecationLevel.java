package kotlin;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/DeprecationLevel;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "HIDDEN", "kotlin-stdlib"}
)
public final class DeprecationLevel extends Enum {
   public static final DeprecationLevel WARNING;
   public static final DeprecationLevel ERROR;
   public static final DeprecationLevel HIDDEN;
   private static final DeprecationLevel[] $VALUES = new DeprecationLevel[]{WARNING = new DeprecationLevel("WARNING", 0), ERROR = new DeprecationLevel("ERROR", 1), HIDDEN = new DeprecationLevel("HIDDEN", 2)};

   private DeprecationLevel(String var1, int var2) {
      super(var1, var2);
   }

   public static DeprecationLevel[] values() {
      return (DeprecationLevel[])$VALUES.clone();
   }

   public static DeprecationLevel valueOf(String var0) {
      return (DeprecationLevel)Enum.valueOf(DeprecationLevel.class, var0);
   }
}
