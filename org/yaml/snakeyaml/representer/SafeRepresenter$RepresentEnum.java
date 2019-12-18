package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentEnum implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentEnum(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      Tag var2 = new Tag(var1.getClass());
      return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), var2), ((Enum)var1).name());
   }
}
