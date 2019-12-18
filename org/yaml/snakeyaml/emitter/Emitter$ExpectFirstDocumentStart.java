package org.yaml.snakeyaml.emitter;

import java.io.IOException;

class Emitter$ExpectFirstDocumentStart implements EmitterState {
   final Emitter this$0;

   private Emitter$ExpectFirstDocumentStart(Emitter var1) {
      super();
      this.this$0 = var1;
   }

   public void expect() throws IOException {
      (new Emitter$ExpectDocumentStart(this.this$0, true)).expect();
   }

   Emitter$ExpectFirstDocumentStart(Emitter var1, Emitter$1 var2) {
      this(var1);
   }
}
