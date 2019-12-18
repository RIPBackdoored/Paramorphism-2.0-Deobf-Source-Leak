package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.events.StreamStartEvent;

class Emitter$ExpectStreamStart implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectStreamStart(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      if (Emitter.access$100(this.this$0) instanceof StreamStartEvent) {
         this.this$0.writeStreamStart();
         Emitter.access$202(this.this$0, new Emitter$ExpectFirstDocumentStart(this.this$0, (Emitter$1)null));
      } else {
         throw new EmitterException("expected StreamStartEvent, but got " + Emitter.access$100(this.this$0));
      }
   }

   Emitter$ExpectStreamStart(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
