package paramorphism-obfuscator;

public final class oj extends Enum {
   public static final oj ze;
   public static final oj zf;
   public static final oj zg;
   public static final oj zh;
   private static final oj[] $VALUES = new oj[]{ze = new oj("PACKAGE", 0), zf = new oj("CLASS", 1), zg = new oj("FIELD", 2), zh = new oj("METHOD", 3)};

   private oj(String var1, int var2) {
      super(var1, var2);
   }

   public static oj[] values() {
      return (oj[])$VALUES.clone();
   }

   public static oj valueOf(String var0) {
      return (oj)Enum.valueOf(oj.class, var0);
   }
}
