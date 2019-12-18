package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingEndEvent;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseFlowSequenceEntryMappingEnd implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseFlowSequenceEntryMappingEnd(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      ParserImpl.access$102(this.this$0, new ParserImpl$ParseFlowSequenceEntry(this.this$0, false));
      Token var1 = this.this$0.scanner.peekToken();
      return new MappingEndEvent(var1.getStartMark(), var1.getEndMark());
   }

   ParserImpl$ParseFlowSequenceEntryMappingEnd(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
