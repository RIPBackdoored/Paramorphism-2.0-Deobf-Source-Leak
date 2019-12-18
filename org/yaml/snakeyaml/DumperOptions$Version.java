package org.yaml.snakeyaml;

public final class DumperOptions$Version extends Enum {
   public static final DumperOptions$Version V1_0 = new DumperOptions$Version("V1_0", 0, new Integer[]{1, 0});
   public static final DumperOptions$Version V1_1 = new DumperOptions$Version("V1_1", 1, new Integer[]{1, 1});
   private Integer[] version;
   private static final DumperOptions$Version[] $VALUES = new DumperOptions$Version[]{V1_0, V1_1};

   public static DumperOptions$Version[] values() {
      return (DumperOptions$Version[])$VALUES.clone();
   }

   public static DumperOptions$Version valueOf(String var0) {
      return (DumperOptions$Version)Enum.valueOf(DumperOptions$Version.class, var0);
   }

   private DumperOptions$Version(String var1, int var2, Integer[] var3) {
      super(var1, var2);
      this.version = var3;
   }

   public int major() {
      return this.version[0];
   }

   public int minor() {
      return this.version[1];
   }

   public String getRepresentation() {
      return this.version[0] + "." + this.version[1];
   }

   public String toString() {
      return "Version: " + this.getRepresentation();
   }
}
