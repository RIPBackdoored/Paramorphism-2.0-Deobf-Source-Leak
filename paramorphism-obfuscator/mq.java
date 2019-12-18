package paramorphism-obfuscator;

public final class mq extends Enum {
   public static final mq vg;
   public static final mq vh;
   public static final mq vi;
   public static final mq vj;
   public static final mq vk;
   private static final mq[] $VALUES = new mq[]{vg = new mq("REMAPPING", 0), vh = new mq("MAIN", 1), vi = new mq("FLOW", 2), vj = new mq("PACKING", 3), vk = new mq("CORRUPTION", 4)};

   private mq(String var1, int var2) {
      super(var1, var2);
   }

   public static mq[] values() {
      return (mq[])$VALUES.clone();
   }

   public static mq valueOf(String var0) {
      return (mq)Enum.valueOf(mq.class, var0);
   }
}
