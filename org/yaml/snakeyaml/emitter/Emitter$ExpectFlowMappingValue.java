package org.yaml.snakeyaml.emitter;

import java.io.IOException;

class Emitter$ExpectFlowMappingValue implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectFlowMappingValue(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      if (Emitter.access$1000(this.this$0) || Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) || Emitter.access$2400(this.this$0)) {
         this.this$0.writeIndent();
      }

      this.this$0.writeIndicator(":", true, false, false);
      Emitter.access$1500(this.this$0).push(new Emitter$ExpectFlowMappingKey(this.this$0, (Emitter$1)null));
      Emitter.access$1600(this.this$0, false, true, false);
   }

   Emitter$ExpectFlowMappingValue(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
