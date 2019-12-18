package kotlin;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
   d2 = {"Lkotlin/Experimental$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "kotlin-stdlib"}
)
public final class Experimental$Level extends Enum {
   public static final Experimental$Level WARNING;
   public static final Experimental$Level ERROR;
   private static final Experimental$Level[] $VALUES = new Experimental$Level[]{WARNING = new Experimental$Level("WARNING", 0), ERROR = new Experimental$Level("ERROR", 1)};

   private Experimental$Level(String var1, int var2) {
      super(var1, var2);
   }

   public static Experimental$Level[] values() {
      return (Experimental$Level[])$VALUES.clone();
   }

   public static Experimental$Level valueOf(String var0) {
      return (Experimental$Level)Enum.valueOf(Experimental$Level.class, var0);
   }
}
