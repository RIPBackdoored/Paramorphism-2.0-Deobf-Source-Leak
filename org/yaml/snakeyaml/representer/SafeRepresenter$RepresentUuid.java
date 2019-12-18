package org.yaml.snakeyaml.representer;

import java.util.UUID;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentUuid implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentUuid(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), new Tag(UUID.class)), var1.toString());
   }
}
