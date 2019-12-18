package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.events.SequenceEndEvent;

class Emitter$ExpectFirstFlowSequenceItem implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectFirstFlowSequenceItem(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      if (Emitter.access$100(this.this$0) instanceof SequenceEndEvent) {
         Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
         Emitter.access$2010(this.this$0);
         this.this$0.writeIndicator("]", false, false, false);
         Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
      } else {
         if (Emitter.access$1000(this.this$0) || Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) && Emitter.access$2300(this.this$0) || Emitter.access$2400(this.this$0)) {
            this.this$0.writeIndent();
         }

         Emitter.access$1500(this.this$0).push(new Emitter$ExpectFlowSequenceItem(this.this$0, (Emitter$1)null));
         Emitter.access$1600(this.this$0, false, false, false);
      }

   }

   Emitter$ExpectFirstFlowSequenceItem(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
