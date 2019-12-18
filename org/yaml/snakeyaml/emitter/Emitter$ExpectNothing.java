package org.yaml.snakeyaml.emitter;

import java.io.IOException;

class Emitter$ExpectNothing implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectNothing(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      throw new EmitterException("expecting nothing, but got " + Emitter.access$100(this.this$0));
   }

   Emitter$ExpectNothing(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
