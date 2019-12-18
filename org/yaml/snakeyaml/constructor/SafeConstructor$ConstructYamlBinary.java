package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class SafeConstructor$ConstructYamlBinary extends AbstractConstruct {
   final SafeConstructor this$0;

   public SafeConstructor$ConstructYamlBinary(SafeConstructor var1) {
      super();
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      String var2 = this.this$0.constructScalar((ScalarNode)var1).toString().replaceAll("\\s", "");
      byte[] var3 = Base64Coder.decode(var2.toCharArray());
      return var3;
   }
}
