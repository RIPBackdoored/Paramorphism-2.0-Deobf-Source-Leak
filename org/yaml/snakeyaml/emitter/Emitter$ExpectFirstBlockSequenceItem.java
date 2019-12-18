package org.yaml.snakeyaml.emitter;

import java.io.IOException;

class Emitter$ExpectFirstBlockSequenceItem implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectFirstBlockSequenceItem(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      (new Emitter$ExpectBlockSequenceItem(this.this$0, true)).expect();
   }

   Emitter$ExpectFirstBlockSequenceItem(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
