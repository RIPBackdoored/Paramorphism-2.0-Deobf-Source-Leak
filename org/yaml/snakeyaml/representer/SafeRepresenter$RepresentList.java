package org.yaml.snakeyaml.representer;

import java.util.List;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentList implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentList(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      return this.this$0.representSequence(this.this$0.getTag(var1.getClass(), Tag.SEQ), (List)var1, (Boolean)null);
   }
}
