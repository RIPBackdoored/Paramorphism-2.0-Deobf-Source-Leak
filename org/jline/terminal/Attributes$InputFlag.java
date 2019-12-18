package org.jline.terminal;

public final class Attributes$InputFlag extends Enum {
   public static final Attributes$InputFlag IGNBRK = new Attributes$InputFlag("IGNBRK", 0);
   public static final Attributes$InputFlag BRKINT = new Attributes$InputFlag("BRKINT", 1);
   public static final Attributes$InputFlag IGNPAR = new Attributes$InputFlag("IGNPAR", 2);
   public static final Attributes$InputFlag PARMRK = new Attributes$InputFlag("PARMRK", 3);
   public static final Attributes$InputFlag INPCK = new Attributes$InputFlag("INPCK", 4);
   public static final Attributes$InputFlag ISTRIP = new Attributes$InputFlag("ISTRIP", 5);
   public static final Attributes$InputFlag INLCR = new Attributes$InputFlag("INLCR", 6);
   public static final Attributes$InputFlag IGNCR = new Attributes$InputFlag("IGNCR", 7);
   public static final Attributes$InputFlag ICRNL = new Attributes$InputFlag("ICRNL", 8);
   public static final Attributes$InputFlag IXON = new Attributes$InputFlag("IXON", 9);
   public static final Attributes$InputFlag IXOFF = new Attributes$InputFlag("IXOFF", 10);
   public static final Attributes$InputFlag IXANY = new Attributes$InputFlag("IXANY", 11);
   public static final Attributes$InputFlag IMAXBEL = new Attributes$InputFlag("IMAXBEL", 12);
   public static final Attributes$InputFlag IUTF8 = new Attributes$InputFlag("IUTF8", 13);
   private static final Attributes$InputFlag[] $VALUES = new Attributes$InputFlag[]{IGNBRK, BRKINT, IGNPAR, PARMRK, INPCK, ISTRIP, INLCR, IGNCR, ICRNL, IXON, IXOFF, IXANY, IMAXBEL, IUTF8};

   public static Attributes$InputFlag[] values() {
      return (Attributes$InputFlag[])$VALUES.clone();
   }

   public static Attributes$InputFlag valueOf(String var0) {
      return (Attributes$InputFlag)Enum.valueOf(Attributes$InputFlag.class, var0);
   }

   private Attributes$InputFlag(String var1, int var2) {
      super(var1, var2);
   }
}
