package org.yaml.snakeyaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.events.Event;

class Yaml$SilentEmitter implements Emitable {
   private List events;

   private Yaml$SilentEmitter() {
      super();
      this.events = new ArrayList(100);
   }

   public List getEvents() {
      return this.events;
   }

   public void emit(Event var1) throws IOException {
      this.events.add(var1);
   }

   Yaml$SilentEmitter(Yaml$1 var1) {
      this();
   }
}
