package org.yaml.snakeyaml.emitter;

import org.yaml.snakeyaml.events.*;
import java.io.*;

private class ExpectBlockSequenceItem implements EmitterState
{
    private boolean first;
    final /* synthetic */ Emitter this$0;
    
    public ExpectBlockSequenceItem(final Emitter this$0, final boolean first) {
        this.this$0 = this$0;
        super();
        this.first = first;
    }
    
    @Override
    public void expect() throws IOException {
        if (!this.first && Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
            Emitter.access$1802(this.this$0, Emitter.access$1900(this.this$0).pop());
            Emitter.access$202(this.this$0, Emitter.access$1500(this.this$0).pop());
        }
        else {
            this.this$0.writeIndent();
            Emitter.access$3300(this.this$0, Emitter.access$3200(this.this$0));
            this.this$0.writeIndicator("-", true, false, true);
            Emitter.access$1500(this.this$0).push(this.this$0.new ExpectBlockSequenceItem(false));
            Emitter.access$1600(this.this$0, false, false, false);
        }
    }
}
