package org.yaml.snakeyaml.parser;

import java.util.Map;
import org.yaml.snakeyaml.DumperOptions$Version;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token$ID;

class ParserImpl$ParseImplicitDocumentStart implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseImplicitDocumentStart(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      if (!this.this$0.scanner.checkToken(Token$ID.Directive, Token$ID.DocumentStart, Token$ID.StreamEnd)) {
         ParserImpl.access$302(this.this$0, new VersionTagsTuple((DumperOptions$Version)null, ParserImpl.access$400()));
         Token var5 = this.this$0.scanner.peekToken();
         Mark var2 = var5.getStartMark();
         DocumentStartEvent var4 = new DocumentStartEvent(var2, var2, false, (DumperOptions$Version)null, (Map)null);
         ParserImpl.access$600(this.this$0).push(new ParserImpl$ParseDocumentEnd(this.this$0, (ParserImpl$1)null));
         ParserImpl.access$102(this.this$0, new ParserImpl$ParseBlockNode(this.this$0, (ParserImpl$1)null));
         return var4;
      } else {
         ParserImpl$ParseDocumentStart var1 = new ParserImpl$ParseDocumentStart(this.this$0, (ParserImpl$1)null);
         return var1.produce();
      }
   }

   ParserImpl$ParseImplicitDocumentStart(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
