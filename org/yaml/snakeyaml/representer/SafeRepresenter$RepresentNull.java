package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentNull implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentNull(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      return this.this$0.representScalar(Tag.NULL, "null");
   }
}
