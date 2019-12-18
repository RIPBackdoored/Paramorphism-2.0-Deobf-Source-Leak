package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.Node;

public final class SafeConstructor$ConstructUndefined extends AbstractConstruct {
   public SafeConstructor$ConstructUndefined() {
      super();
   }

   public Object construct(Node var1) {
      throw new ConstructorException((String)null, (Mark)null, "could not determine a constructor for the tag " + var1.getTag(), var1.getStartMark());
   }
}
