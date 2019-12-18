package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.tokens.*;

private class ParseDocumentContent implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseDocumentContent(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        if (this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd)) {
            final Event event = ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
            ParserImpl.access$102(this.this$0, ParserImpl.access$600(this.this$0).pop());
            return event;
        }
        final Production p = this.this$0.new ParseBlockNode();
        return p.produce();
    }
    
    ParseDocumentContent(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
