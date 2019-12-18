package org.jline.terminal;

public final class Attributes$OutputFlag extends Enum {
   public static final Attributes$OutputFlag OPOST = new Attributes$OutputFlag("OPOST", 0);
   public static final Attributes$OutputFlag ONLCR = new Attributes$OutputFlag("ONLCR", 1);
   public static final Attributes$OutputFlag OXTABS = new Attributes$OutputFlag("OXTABS", 2);
   public static final Attributes$OutputFlag ONOEOT = new Attributes$OutputFlag("ONOEOT", 3);
   public static final Attributes$OutputFlag OCRNL = new Attributes$OutputFlag("OCRNL", 4);
   public static final Attributes$OutputFlag ONOCR = new Attributes$OutputFlag("ONOCR", 5);
   public static final Attributes$OutputFlag ONLRET = new Attributes$OutputFlag("ONLRET", 6);
   public static final Attributes$OutputFlag OFILL = new Attributes$OutputFlag("OFILL", 7);
   public static final Attributes$OutputFlag NLDLY = new Attributes$OutputFlag("NLDLY", 8);
   public static final Attributes$OutputFlag TABDLY = new Attributes$OutputFlag("TABDLY", 9);
   public static final Attributes$OutputFlag CRDLY = new Attributes$OutputFlag("CRDLY", 10);
   public static final Attributes$OutputFlag FFDLY = new Attributes$OutputFlag("FFDLY", 11);
   public static final Attributes$OutputFlag BSDLY = new Attributes$OutputFlag("BSDLY", 12);
   public static final Attributes$OutputFlag VTDLY = new Attributes$OutputFlag("VTDLY", 13);
   public static final Attributes$OutputFlag OFDEL = new Attributes$OutputFlag("OFDEL", 14);
   private static final Attributes$OutputFlag[] $VALUES = new Attributes$OutputFlag[]{OPOST, ONLCR, OXTABS, ONOEOT, OCRNL, ONOCR, ONLRET, OFILL, NLDLY, TABDLY, CRDLY, FFDLY, BSDLY, VTDLY, OFDEL};

   public static Attributes$OutputFlag[] values() {
      return (Attributes$OutputFlag[])$VALUES.clone();
   }

   public static Attributes$OutputFlag valueOf(String var0) {
      return (Attributes$OutputFlag)Enum.valueOf(Attributes$OutputFlag.class, var0);
   }

   private Attributes$OutputFlag(String var1, int var2) {
      super(var1, var2);
   }
}
