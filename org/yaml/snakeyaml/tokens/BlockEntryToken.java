package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class BlockEntryToken extends Token {
   public BlockEntryToken(Mark var1, Mark var2) {
      super(var1, var2);
   }

   public Token$ID getTokenId() {
      return Token$ID.BlockEntry;
   }
}
