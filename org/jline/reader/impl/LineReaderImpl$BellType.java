package org.jline.reader.impl;

public final class LineReaderImpl$BellType extends Enum {
   public static final LineReaderImpl$BellType NONE = new LineReaderImpl$BellType("NONE", 0);
   public static final LineReaderImpl$BellType AUDIBLE = new LineReaderImpl$BellType("AUDIBLE", 1);
   public static final LineReaderImpl$BellType VISIBLE = new LineReaderImpl$BellType("VISIBLE", 2);
   private static final LineReaderImpl$BellType[] $VALUES = new LineReaderImpl$BellType[]{NONE, AUDIBLE, VISIBLE};

   public static LineReaderImpl$BellType[] values() {
      return (LineReaderImpl$BellType[])$VALUES.clone();
   }

   public static LineReaderImpl$BellType valueOf(String var0) {
      return (LineReaderImpl$BellType)Enum.valueOf(LineReaderImpl$BellType.class, var0);
   }

   private LineReaderImpl$BellType(String var1, int var2) {
      super(var1, var2);
   }
}
