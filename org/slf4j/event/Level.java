package org.slf4j.event;

public final class Level extends Enum {
   public static final Level ERROR = new Level("ERROR", 0, 40, "ERROR");
   public static final Level WARN = new Level("WARN", 1, 30, "WARN");
   public static final Level INFO = new Level("INFO", 2, 20, "INFO");
   public static final Level DEBUG = new Level("DEBUG", 3, 10, "DEBUG");
   public static final Level TRACE = new Level("TRACE", 4, 0, "TRACE");
   private int levelInt;
   private String levelStr;
   private static final Level[] $VALUES = new Level[]{ERROR, WARN, INFO, DEBUG, TRACE};

   public static Level[] values() {
      return (Level[])$VALUES.clone();
   }

   public static Level valueOf(String var0) {
      return (Level)Enum.valueOf(Level.class, var0);
   }

   private Level(String var1, int var2, int var3, String var4) {
      super(var1, var2);
      this.levelInt = var3;
      this.levelStr = var4;
   }

   public int toInt() {
      return this.levelInt;
   }

   public String toString() {
      return this.levelStr;
   }
}
