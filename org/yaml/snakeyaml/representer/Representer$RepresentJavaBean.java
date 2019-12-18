package org.yaml.snakeyaml.representer;

import java.beans.IntrospectionException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;

public class Representer$RepresentJavaBean implements Represent {
   final Representer this$0;

   protected Representer$RepresentJavaBean(Representer var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      MappingNode var10000;
      try {
         var10000 = this.this$0.representJavaBean(this.this$0.getProperties(var1.getClass()), var1);
      } catch (IntrospectionException var3) {
         throw new YAMLException(var3);
      }

      return var10000;
   }
}
