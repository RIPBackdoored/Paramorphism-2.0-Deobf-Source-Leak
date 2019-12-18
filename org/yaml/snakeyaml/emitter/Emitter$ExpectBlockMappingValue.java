package org.yaml.snakeyaml.emitter;

import java.io.IOException;

class Emitter$ExpectBlockMappingValue implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectBlockMappingValue(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      this.this$0.writeIndent();
      this.this$0.writeIndicator(":", true, false, true);
      Emitter.access$1500(this.this$0).push(new Emitter$ExpectBlockMappingKey(this.this$0, false));
      Emitter.access$1600(this.this$0, false, true, false);
   }

   Emitter$ExpectBlockMappingValue(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
