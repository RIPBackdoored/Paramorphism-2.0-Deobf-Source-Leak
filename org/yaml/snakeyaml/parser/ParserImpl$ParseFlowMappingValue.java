package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token$ID;

class ParserImpl$ParseFlowMappingValue implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseFlowMappingValue(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      Token var1;
      if (this.this$0.scanner.checkToken(Token$ID.Value)) {
         var1 = this.this$0.scanner.getToken();
         if (!this.this$0.scanner.checkToken(Token$ID.FlowEntry, Token$ID.FlowMappingEnd)) {
            ParserImpl.access$600(this.this$0).push(new ParserImpl$ParseFlowMappingKey(this.this$0, false));
            return ParserImpl.access$2400(this.this$0);
         } else {
            ParserImpl.access$102(this.this$0, new ParserImpl$ParseFlowMappingKey(this.this$0, false));
            return ParserImpl.access$1200(this.this$0, var1.getEndMark());
         }
      } else {
         ParserImpl.access$102(this.this$0, new ParserImpl$ParseFlowMappingKey(this.this$0, false));
         var1 = this.this$0.scanner.peekToken();
         return ParserImpl.access$1200(this.this$0, var1.getStartMark());
      }
   }

   ParserImpl$ParseFlowMappingValue(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
