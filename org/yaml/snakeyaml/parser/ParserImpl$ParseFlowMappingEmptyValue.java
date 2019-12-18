package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;

class ParserImpl$ParseFlowMappingEmptyValue implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseFlowMappingEmptyValue(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      ParserImpl.access$102(this.this$0, new ParserImpl$ParseFlowMappingKey(this.this$0, false));
      return ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
   }

   ParserImpl$ParseFlowMappingEmptyValue(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
