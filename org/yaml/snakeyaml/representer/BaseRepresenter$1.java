package org.yaml.snakeyaml.representer;

import java.util.IdentityHashMap;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.Node;

class BaseRepresenter$1 extends IdentityHashMap {
   private static final long serialVersionUID = -5576159264232131854L;
   final BaseRepresenter this$0;

   BaseRepresenter$1(BaseRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node put(Object var1, Node var2) {
      return (Node)super.put(var1, new AnchorNode(var2));
   }

   public Object put(Object var1, Object var2) {
      return this.put(var1, (Node)var2);
   }
}
