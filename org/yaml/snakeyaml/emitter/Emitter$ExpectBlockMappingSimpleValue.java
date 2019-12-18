package org.yaml.snakeyaml.emitter;

import java.io.*;

private class ExpectBlockMappingSimpleValue implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectBlockMappingSimpleValue(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        this.this$0.writeIndicator(":", false, false, false);
        Emitter.access$1500(this.this$0).push(this.this$0.new ExpectBlockMappingKey(false));
        Emitter.access$1600(this.this$0, false, true, false);
    }
    
    ExpectBlockMappingSimpleValue(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
