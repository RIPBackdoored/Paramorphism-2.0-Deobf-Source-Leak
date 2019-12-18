package org.yaml.snakeyaml.emitter;

import java.io.IOException;

class Emitter$ExpectDocumentRoot implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectDocumentRoot(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      Emitter.access$1500(this.this$0).push(new Emitter$ExpectDocumentEnd(this.this$0, (Emitter$1)null));
      Emitter.access$1600(this.this$0, true, false, false);
   }

   Emitter$ExpectDocumentRoot(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
