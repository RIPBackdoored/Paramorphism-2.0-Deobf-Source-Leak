package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.StreamStartEvent;
import org.yaml.snakeyaml.tokens.StreamStartToken;

class ParserImpl$ParseStreamStart implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseStreamStart(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      StreamStartToken var1 = (StreamStartToken)this.this$0.scanner.getToken();
      StreamStartEvent var2 = new StreamStartEvent(var1.getStartMark(), var1.getEndMark());
      ParserImpl.access$102(this.this$0, new ParserImpl$ParseImplicitDocumentStart(this.this$0, (ParserImpl$1)null));
      return var2;
   }

   ParserImpl$ParseStreamStart(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
