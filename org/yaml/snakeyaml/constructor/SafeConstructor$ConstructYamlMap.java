package org.yaml.snakeyaml.constructor;

import java.util.Map;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;

public class SafeConstructor$ConstructYamlMap implements Construct {
   final SafeConstructor this$0;

   public SafeConstructor$ConstructYamlMap(SafeConstructor var1) {
      super();
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      return var1.isTwoStepsConstruction() ? this.this$0.createDefaultMap() : this.this$0.constructMapping((MappingNode)var1);
   }

   public void construct2ndStep(Node var1, Object var2) {
      if (var1.isTwoStepsConstruction()) {
         this.this$0.constructMapping2ndStep((MappingNode)var1, (Map)var2);
      } else {
         throw new YAMLException("Unexpected recursive mapping structure. Node: " + var1);
      }
   }
}
