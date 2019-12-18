package org.jline.reader;

public final class LineReader$RegionType extends Enum {
   public static final LineReader$RegionType NONE = new LineReader$RegionType("NONE", 0);
   public static final LineReader$RegionType CHAR = new LineReader$RegionType("CHAR", 1);
   public static final LineReader$RegionType LINE = new LineReader$RegionType("LINE", 2);
   public static final LineReader$RegionType PASTE = new LineReader$RegionType("PASTE", 3);
   private static final LineReader$RegionType[] $VALUES = new LineReader$RegionType[]{NONE, CHAR, LINE, PASTE};

   public static LineReader$RegionType[] values() {
      return (LineReader$RegionType[])$VALUES.clone();
   }

   public static LineReader$RegionType valueOf(String var0) {
      return (LineReader$RegionType)Enum.valueOf(LineReader$RegionType.class, var0);
   }

   private LineReader$RegionType(String var1, int var2) {
      super(var1, var2);
   }
}
