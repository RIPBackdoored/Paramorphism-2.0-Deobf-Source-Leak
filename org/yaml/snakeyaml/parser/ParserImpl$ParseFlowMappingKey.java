package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.events.*;

private class ParseFlowMappingKey implements Production
{
    private boolean first;
    final /* synthetic */ ParserImpl this$0;
    
    public ParseFlowMappingKey(final ParserImpl this$0, final boolean first) {
        this.this$0 = this$0;
        super();
        this.first = false;
        this.first = first;
    }
    
    @Override
    public Event produce() {
        if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
            if (!this.first) {
                if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry)) {
                    final Token token = this.this$0.scanner.peekToken();
                    throw new ParserException("while parsing a flow mapping", ParserImpl.access$1100(this.this$0).pop(), "expected ',' or '}', but got " + token.getTokenId(), token.getStartMark());
                }
                this.this$0.scanner.getToken();
            }
            if (this.this$0.scanner.checkToken(Token.ID.Key)) {
                final Token token = this.this$0.scanner.getToken();
                if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
                    ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowMappingValue());
                    return ParserImpl.access$2400(this.this$0);
                }
                ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingValue());
                return ParserImpl.access$1200(this.this$0, token.getEndMark());
            }
            else if (!this.this$0.scanner.checkToken(Token.ID.FlowMappingEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowMappingEmptyValue());
                return ParserImpl.access$2400(this.this$0);
            }
        }
        final Token token = this.this$0.scanner.getToken();
        final Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
        ParserImpl.access$102(this.this$0, ParserImpl.access$600(this.this$0).pop());
        ParserImpl.access$1100(this.this$0).pop();
        return event;
    }
}
