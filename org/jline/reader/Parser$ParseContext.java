package org.jline.reader;

public final class Parser$ParseContext extends Enum {
   public static final Parser$ParseContext UNSPECIFIED = new Parser$ParseContext("UNSPECIFIED", 0);
   public static final Parser$ParseContext ACCEPT_LINE = new Parser$ParseContext("ACCEPT_LINE", 1);
   public static final Parser$ParseContext COMPLETE = new Parser$ParseContext("COMPLETE", 2);
   public static final Parser$ParseContext SECONDARY_PROMPT = new Parser$ParseContext("SECONDARY_PROMPT", 3);
   private static final Parser$ParseContext[] $VALUES = new Parser$ParseContext[]{UNSPECIFIED, ACCEPT_LINE, COMPLETE, SECONDARY_PROMPT};

   public static Parser$ParseContext[] values() {
      return (Parser$ParseContext[])$VALUES.clone();
   }

   public static Parser$ParseContext valueOf(String var0) {
      return (Parser$ParseContext)Enum.valueOf(Parser$ParseContext.class, var0);
   }

   private Parser$ParseContext(String var1, int var2) {
      super(var1, var2);
   }
}
