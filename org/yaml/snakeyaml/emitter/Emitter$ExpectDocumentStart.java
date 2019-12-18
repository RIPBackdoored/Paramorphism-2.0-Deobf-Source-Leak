package org.yaml.snakeyaml.emitter;

import org.yaml.snakeyaml.events.*;
import java.util.*;
import java.io.*;

private class ExpectDocumentStart implements EmitterState
{
    private boolean first;
    final /* synthetic */ Emitter this$0;
    
    public ExpectDocumentStart(final Emitter this$0, final boolean first) {
        this.this$0 = this$0;
        super();
        this.first = first;
    }
    
    @Override
    public void expect() throws IOException {
        if (Emitter.access$100(this.this$0) instanceof DocumentStartEvent) {
            final DocumentStartEvent ev = (DocumentStartEvent)Emitter.access$100(this.this$0);
            if ((ev.getVersion() != null || ev.getTags() != null) && Emitter.access$400(this.this$0)) {
                this.this$0.writeIndicator("...", true, false, false);
                this.this$0.writeIndent();
            }
            if (ev.getVersion() != null) {
                final String versionText = Emitter.access$500(this.this$0, ev.getVersion());
                this.this$0.writeVersionDirective(versionText);
            }
            Emitter.access$602(this.this$0, new LinkedHashMap(Emitter.access$700()));
            if (ev.getTags() != null) {
                final Set<String> handles = new TreeSet<String>(ev.getTags().keySet());
                for (final String handle : handles) {
                    final String prefix = ev.getTags().get(handle);
                    Emitter.access$600(this.this$0).put(prefix, handle);
                    final String handleText = Emitter.access$800(this.this$0, handle);
                    final String prefixText = Emitter.access$900(this.this$0, prefix);
                    this.this$0.writeTagDirective(handleText, prefixText);
                }
            }
            final boolean implicit = this.first && !ev.getExplicit() && !Emitter.access$1000(this.this$0) && ev.getVersion() == null && (ev.getTags() == null || ev.getTags().isEmpty()) && !Emitter.access$1100(this.this$0);
            if (!implicit) {
                this.this$0.writeIndent();
                this.this$0.writeIndicator("---", true, false, false);
                if (Emitter.access$1000(this.this$0)) {
                    this.this$0.writeIndent();
                }
            }
            Emitter.access$202(this.this$0, this.this$0.new ExpectDocumentRoot());
        }
        else {
            if (!(Emitter.access$100(this.this$0) instanceof StreamEndEvent)) {
                throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.access$100(this.this$0));
            }
            this.this$0.writeStreamEnd();
            Emitter.access$202(this.this$0, this.this$0.new ExpectNothing());
        }
    }
}
