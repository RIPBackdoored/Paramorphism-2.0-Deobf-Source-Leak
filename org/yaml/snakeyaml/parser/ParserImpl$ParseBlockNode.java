package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;

private class ParseBlockNode implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseBlockNode(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        return ParserImpl.access$1300(this.this$0, true, false);
    }
    
    ParseBlockNode(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
