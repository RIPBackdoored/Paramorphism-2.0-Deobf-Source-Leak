package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.events.*;

private class ParseBlockSequenceEntry implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseBlockSequenceEntry(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        if (this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
            final BlockEntryToken token = (BlockEntryToken)this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.BlockEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseBlockSequenceEntry());
                return this.this$0.new ParseBlockNode().produce();
            }
            ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockSequenceEntry());
            return ParserImpl.access$1200(this.this$0, token.getEndMark());
        }
        else {
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                final Token token2 = this.this$0.scanner.peekToken();
                throw new ParserException("while parsing a block collection", ParserImpl.access$1100(this.this$0).pop(), "expected <block end>, but found " + token2.getTokenId(), token2.getStartMark());
            }
            final Token token2 = this.this$0.scanner.getToken();
            final Event event = new SequenceEndEvent(token2.getStartMark(), token2.getEndMark());
            ParserImpl.access$102(this.this$0, ParserImpl.access$600(this.this$0).pop());
            ParserImpl.access$1100(this.this$0).pop();
            return event;
        }
    }
    
    ParseBlockSequenceEntry(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
