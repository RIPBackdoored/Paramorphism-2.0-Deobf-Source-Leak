package org.yaml.snakeyaml.representer;

import java.util.Iterator;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentIterator implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentIterator(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      Iterator var2 = (Iterator)var1;
      return this.this$0.representSequence(this.this$0.getTag(var1.getClass(), Tag.SEQ), new SafeRepresenter$IteratorWrapper(var2), (Boolean)null);
   }
}
