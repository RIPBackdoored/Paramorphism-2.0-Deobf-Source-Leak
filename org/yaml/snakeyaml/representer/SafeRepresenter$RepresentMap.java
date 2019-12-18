package org.yaml.snakeyaml.representer;

import java.util.Map;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentMap implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentMap(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      return this.this$0.representMapping(this.this$0.getTag(var1.getClass(), Tag.MAP), (Map)var1, (Boolean)null);
   }
}
