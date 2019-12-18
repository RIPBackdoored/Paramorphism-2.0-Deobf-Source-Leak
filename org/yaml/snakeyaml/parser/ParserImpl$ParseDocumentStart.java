package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.error.*;

private class ParseDocumentStart implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseDocumentStart(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        while (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
            this.this$0.scanner.getToken();
        }
        Event event;
        if (!this.this$0.scanner.checkToken(Token.ID.StreamEnd)) {
            Token token = this.this$0.scanner.peekToken();
            final Mark startMark = token.getStartMark();
            final VersionTagsTuple tuple = ParserImpl.access$900(this.this$0);
            if (!this.this$0.scanner.checkToken(Token.ID.DocumentStart)) {
                throw new ParserException(null, null, "expected '<document start>', but found " + this.this$0.scanner.peekToken().getTokenId(), this.this$0.scanner.peekToken().getStartMark());
            }
            token = this.this$0.scanner.getToken();
            final Mark endMark = token.getEndMark();
            event = new DocumentStartEvent(startMark, endMark, true, tuple.getVersion(), tuple.getTags());
            ParserImpl.access$600(this.this$0).push(this.this$0.new ParseDocumentEnd());
            ParserImpl.access$102(this.this$0, this.this$0.new ParseDocumentContent());
        }
        else {
            final StreamEndToken token2 = (StreamEndToken)this.this$0.scanner.getToken();
            event = new StreamEndEvent(token2.getStartMark(), token2.getEndMark());
            if (!ParserImpl.access$600(this.this$0).isEmpty()) {
                throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.access$600(this.this$0));
            }
            if (!ParserImpl.access$1100(this.this$0).isEmpty()) {
                throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.access$1100(this.this$0));
            }
            ParserImpl.access$102(this.this$0, null);
        }
        return event;
    }
    
    ParseDocumentStart(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
