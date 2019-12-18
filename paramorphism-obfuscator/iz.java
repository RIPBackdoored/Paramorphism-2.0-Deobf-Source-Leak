package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public final class iz {
   public static final int[] ny = new int[JsonNodeType.values().length];

   static {
      ny[JsonNodeType.STRING.ordinal()] = 1;
      ny[JsonNodeType.OBJECT.ordinal()] = 2;
   }
}
