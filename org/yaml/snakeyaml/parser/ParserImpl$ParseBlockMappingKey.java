package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token$ID;

class ParserImpl$ParseBlockMappingKey implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseBlockMappingKey(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      Token var1;
      if (this.this$0.scanner.checkToken(Token$ID.Key)) {
         var1 = this.this$0.scanner.getToken();
         if (!this.this$0.scanner.checkToken(Token$ID.Key, Token$ID.Value, Token$ID.BlockEnd)) {
            ParserImpl.access$600(this.this$0).push(new ParserImpl$ParseBlockMappingValue(this.this$0, (ParserImpl$1)null));
            return ParserImpl.access$2200(this.this$0);
         } else {
            ParserImpl.access$102(this.this$0, new ParserImpl$ParseBlockMappingValue(this.this$0, (ParserImpl$1)null));
            return ParserImpl.access$1200(this.this$0, var1.getEndMark());
         }
      } else if (!this.this$0.scanner.checkToken(Token$ID.BlockEnd)) {
         var1 = this.this$0.scanner.peekToken();
         throw new ParserException("while parsing a block mapping", (Mark)ParserImpl.access$1100(this.this$0).pop(), "expected <block end>, but found " + var1.getTokenId(), var1.getStartMark());
      } else {
         var1 = this.this$0.scanner.getToken();
         MappingEndEvent var2 = new MappingEndEvent(var1.getStartMark(), var1.getEndMark());
         ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
         ParserImpl.access$1100(this.this$0).pop();
         return var2;
      }
   }

   ParserImpl$ParseBlockMappingKey(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
