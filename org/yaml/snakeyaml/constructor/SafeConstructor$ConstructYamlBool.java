package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlBool extends AbstractConstruct {
   final SafeConstructor this$0;

   public SafeConstructor$ConstructYamlBool(SafeConstructor var1) {
      super();
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      String var2 = (String)this.this$0.constructScalar((ScalarNode)var1);
      return SafeConstructor.access$000().get(var2.toLowerCase());
   }
}
