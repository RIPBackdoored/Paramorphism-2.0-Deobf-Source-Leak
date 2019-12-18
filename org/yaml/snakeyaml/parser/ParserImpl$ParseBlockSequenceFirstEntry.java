package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.tokens.Token;

class ParserImpl$ParseBlockSequenceFirstEntry implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseBlockSequenceFirstEntry(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      Token var1 = this.this$0.scanner.getToken();
      ParserImpl.access$1100(this.this$0).push(var1.getStartMark());
      return (new ParserImpl$ParseBlockSequenceEntry(this.this$0, (ParserImpl$1)null)).produce();
   }

   ParserImpl$ParseBlockSequenceFirstEntry(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
