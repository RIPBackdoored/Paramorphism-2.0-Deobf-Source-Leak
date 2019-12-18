package org.yaml.snakeyaml.emitter;

import java.io.IOException;

class Emitter$ExpectFlowMappingSimpleValue implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectFlowMappingSimpleValue(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      this.this$0.writeIndicator(":", false, false, false);
      Emitter.access$1500(this.this$0).push(new Emitter$ExpectFlowMappingKey(this.this$0, (Emitter$1)null));
      Emitter.access$1600(this.this$0, false, true, false);
   }

   Emitter$ExpectFlowMappingSimpleValue(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
