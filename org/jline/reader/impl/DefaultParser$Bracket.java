package org.jline.reader.impl;

public final class DefaultParser$Bracket extends Enum {
   public static final DefaultParser$Bracket ROUND = new DefaultParser$Bracket("ROUND", 0);
   public static final DefaultParser$Bracket CURLY = new DefaultParser$Bracket("CURLY", 1);
   public static final DefaultParser$Bracket SQUARE = new DefaultParser$Bracket("SQUARE", 2);
   public static final DefaultParser$Bracket ANGLE = new DefaultParser$Bracket("ANGLE", 3);
   private static final DefaultParser$Bracket[] $VALUES = new DefaultParser$Bracket[]{ROUND, CURLY, SQUARE, ANGLE};

   public static DefaultParser$Bracket[] values() {
      return (DefaultParser$Bracket[])$VALUES.clone();
   }

   public static DefaultParser$Bracket valueOf(String var0) {
      return (DefaultParser$Bracket)Enum.valueOf(DefaultParser$Bracket.class, var0);
   }

   private DefaultParser$Bracket(String var1, int var2) {
      super(var1, var2);
   }
}
