package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.SequenceEndEvent;
import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token$ID;

class ParserImpl$ParseIndentlessSequenceEntry implements Production {
   final ParserImpl this$0;

   private ParserImpl$ParseIndentlessSequenceEntry(ParserImpl var1) {
      super();
      this.this$0 = var1;
   }

   public Event produce() {
      Token var1;
      if (this.this$0.scanner.checkToken(Token$ID.BlockEntry)) {
         var1 = this.this$0.scanner.getToken();
         if (!this.this$0.scanner.checkToken(Token$ID.BlockEntry, Token$ID.Key, Token$ID.Value, Token$ID.BlockEnd)) {
            ParserImpl.access$600(this.this$0).push(new ParserImpl$ParseIndentlessSequenceEntry(this.this$0));
            return (new ParserImpl$ParseBlockNode(this.this$0, (ParserImpl$1)null)).produce();
         } else {
            ParserImpl.access$102(this.this$0, new ParserImpl$ParseIndentlessSequenceEntry(this.this$0));
            return ParserImpl.access$1200(this.this$0, var1.getEndMark());
         }
      } else {
         var1 = this.this$0.scanner.peekToken();
         SequenceEndEvent var2 = new SequenceEndEvent(var1.getStartMark(), var1.getEndMark());
         ParserImpl.access$102(this.this$0, (Production)ParserImpl.access$600(this.this$0).pop());
         return var2;
      }
   }

   ParserImpl$ParseIndentlessSequenceEntry(ParserImpl var1, ParserImpl$1 var2) {
      this(var1);
   }
}
