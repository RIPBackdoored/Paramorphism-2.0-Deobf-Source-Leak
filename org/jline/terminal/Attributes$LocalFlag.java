package org.jline.terminal;

public final class Attributes$LocalFlag extends Enum {
   public static final Attributes$LocalFlag ECHOKE = new Attributes$LocalFlag("ECHOKE", 0);
   public static final Attributes$LocalFlag ECHOE = new Attributes$LocalFlag("ECHOE", 1);
   public static final Attributes$LocalFlag ECHOK = new Attributes$LocalFlag("ECHOK", 2);
   public static final Attributes$LocalFlag ECHO = new Attributes$LocalFlag("ECHO", 3);
   public static final Attributes$LocalFlag ECHONL = new Attributes$LocalFlag("ECHONL", 4);
   public static final Attributes$LocalFlag ECHOPRT = new Attributes$LocalFlag("ECHOPRT", 5);
   public static final Attributes$LocalFlag ECHOCTL = new Attributes$LocalFlag("ECHOCTL", 6);
   public static final Attributes$LocalFlag ISIG = new Attributes$LocalFlag("ISIG", 7);
   public static final Attributes$LocalFlag ICANON = new Attributes$LocalFlag("ICANON", 8);
   public static final Attributes$LocalFlag ALTWERASE = new Attributes$LocalFlag("ALTWERASE", 9);
   public static final Attributes$LocalFlag IEXTEN = new Attributes$LocalFlag("IEXTEN", 10);
   public static final Attributes$LocalFlag EXTPROC = new Attributes$LocalFlag("EXTPROC", 11);
   public static final Attributes$LocalFlag TOSTOP = new Attributes$LocalFlag("TOSTOP", 12);
   public static final Attributes$LocalFlag FLUSHO = new Attributes$LocalFlag("FLUSHO", 13);
   public static final Attributes$LocalFlag NOKERNINFO = new Attributes$LocalFlag("NOKERNINFO", 14);
   public static final Attributes$LocalFlag PENDIN = new Attributes$LocalFlag("PENDIN", 15);
   public static final Attributes$LocalFlag NOFLSH = new Attributes$LocalFlag("NOFLSH", 16);
   private static final Attributes$LocalFlag[] $VALUES = new Attributes$LocalFlag[]{ECHOKE, ECHOE, ECHOK, ECHO, ECHONL, ECHOPRT, ECHOCTL, ISIG, ICANON, ALTWERASE, IEXTEN, EXTPROC, TOSTOP, FLUSHO, NOKERNINFO, PENDIN, NOFLSH};

   public static Attributes$LocalFlag[] values() {
      return (Attributes$LocalFlag[])$VALUES.clone();
   }

   public static Attributes$LocalFlag valueOf(String var0) {
      return (Attributes$LocalFlag)Enum.valueOf(Attributes$LocalFlag.class, var0);
   }

   private Attributes$LocalFlag(String var1, int var2) {
      super(var1, var2);
   }
}
