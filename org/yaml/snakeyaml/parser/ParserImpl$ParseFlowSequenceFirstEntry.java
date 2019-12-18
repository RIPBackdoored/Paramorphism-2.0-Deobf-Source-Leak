package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowSequenceFirstEntry implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseFlowSequenceFirstEntry(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      Token var1 = this.this$0.scanner.getToken();
      ParserImpl.access$1100(this.this$0).push(var1.getStartMark());
      return (new ParserImpl$ParseFlowSequenceEntry(this.this$0, true)).produce();
   }

   ParserImpl$ParseFlowSequenceFirstEntry(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
