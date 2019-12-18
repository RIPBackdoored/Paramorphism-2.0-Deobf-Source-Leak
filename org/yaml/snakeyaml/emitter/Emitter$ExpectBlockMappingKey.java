package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.events.MappingEndEvent;

class Emitter$ExpectBlockMappingKey implements EmitterState {
   private boolean first;
   final Emitter this$0;

   public Emitter$ExpectBlockMappingKey(Emitter var1, boolean var2) {
      super();
      this.this$0 = var1;
      this.first = var2;
   }

   public void expect() throws IOException {
      if (!this.first && Emitter.access$100(this.this$0) instanceof MappingEndEvent) {
         Emitter.access$1802(this.this$0, (Integer)Emitter.access$1900(this.this$0).pop());
         Emitter.access$202(this.this$0, (EmitterState)Emitter.access$1500(this.this$0).pop());
      } else {
         this.this$0.writeIndent();
         if (Emitter.access$2700(this.this$0)) {
            Emitter.access$1500(this.this$0).push(new Emitter$ExpectBlockMappingSimpleValue(this.this$0, (Emitter$1)null));
            Emitter.access$1600(this.this$0, false, true, true);
         } else {
            this.this$0.writeIndicator("?", true, false, true);
            Emitter.access$1500(this.this$0).push(new Emitter$ExpectBlockMappingValue(this.this$0, (Emitter$1)null));
            Emitter.access$1600(this.this$0, false, true, false);
         }
      }

   }
}
