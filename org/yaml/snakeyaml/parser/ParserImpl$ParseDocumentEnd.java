package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.DocumentEndEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token$ID;

class ParserImpl$ParseDocumentEnd implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseDocumentEnd(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      Token var1 = this.this$0.scanner.peekToken();
      Mark var2 = var1.getStartMark();
      Mark var3 = var2;
      boolean var4 = false;
      if (this.this$0.scanner.checkToken(Token$ID.DocumentEnd)) {
         var1 = this.this$0.scanner.getToken();
         var3 = var1.getEndMark();
         var4 = true;
      }

      DocumentEndEvent var5 = new DocumentEndEvent(var2, var3, var4);
      ParserImpl.access$102(this.this$0, new ParserImpl$ParseDocumentStart(this.this$0, (ParserImpl$1)null));
      return var5;
   }

   ParserImpl$ParseDocumentEnd(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
