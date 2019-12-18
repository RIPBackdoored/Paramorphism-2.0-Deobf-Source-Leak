package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlStr extends AbstractConstruct {
   final SafeConstructor this$0;

   public SafeConstructor$ConstructYamlStr(SafeConstructor var1) {
      super();
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      return this.this$0.constructScalar((ScalarNode)var1);
   }
}
