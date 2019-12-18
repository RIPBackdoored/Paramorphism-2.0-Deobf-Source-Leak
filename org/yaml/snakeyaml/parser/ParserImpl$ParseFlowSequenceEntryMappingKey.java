package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token$ID;

class ParserImpl$ParseFlowSequenceEntryMappingKey implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseFlowSequenceEntryMappingKey(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      Token var1 = this.this$0.scanner.getToken();
      if (!this.this$0.scanner.checkToken(Token$ID.Value, Token$ID.FlowEntry, Token$ID.FlowSequenceEnd)) {
         ParserImpl.access$600(this.this$0).push(new ParserImpl$ParseFlowSequenceEntryMappingValue(this.this$0, (ParserImpl$1)null));
         return ParserImpl.access$2400(this.this$0);
      } else {
         ParserImpl.access$102(this.this$0, new ParserImpl$ParseFlowSequenceEntryMappingValue(this.this$0, (ParserImpl$1)null));
         return ParserImpl.access$1200(this.this$0, var1.getEndMark());
      }
   }

   ParserImpl$ParseFlowSequenceEntryMappingKey(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
