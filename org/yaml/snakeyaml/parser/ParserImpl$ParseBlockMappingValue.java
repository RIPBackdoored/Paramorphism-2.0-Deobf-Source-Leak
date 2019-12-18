package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.tokens.*;

private class ParseBlockMappingValue implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseBlockMappingValue(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
            ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockMappingKey());
            final Token token = this.this$0.scanner.peekToken();
            return ParserImpl.access$1200(this.this$0, token.getStartMark());
        }
        final Token token = this.this$0.scanner.getToken();
        if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
            ParserImpl.access$600(this.this$0).push(this.this$0.new ParseBlockMappingKey());
            return ParserImpl.access$2200(this.this$0);
        }
        ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockMappingKey());
        return ParserImpl.access$1200(this.this$0, token.getEndMark());
    }
    
    ParseBlockMappingValue(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
