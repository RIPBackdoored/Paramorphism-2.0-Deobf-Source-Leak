package org.yaml.snakeyaml.constructor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class SafeConstructor$ConstructYamlOmap extends AbstractConstruct {
   final SafeConstructor this$0;

   public SafeConstructor$ConstructYamlOmap(SafeConstructor var1) {
      super();
      this.this$0 = var1;
   }

   public Object construct(Node var1) {
      LinkedHashMap var2 = new LinkedHashMap();
      if (!(var1 instanceof SequenceNode)) {
         throw new ConstructorException("while constructing an ordered map", var1.getStartMark(), "expected a sequence, but found " + var1.getNodeId(), var1.getStartMark());
      } else {
         SequenceNode var3 = (SequenceNode)var1;
         Iterator var4 = var3.getValue().iterator();

         while(var4.hasNext()) {
            Node var5 = (Node)var4.next();
            if (!(var5 instanceof MappingNode)) {
               throw new ConstructorException("while constructing an ordered map", var1.getStartMark(), "expected a mapping of length 1, but found " + var5.getNodeId(), var5.getStartMark());
            }

            MappingNode var6 = (MappingNode)var5;
            if (var6.getValue().size() != 1) {
               throw new ConstructorException("while constructing an ordered map", var1.getStartMark(), "expected a single mapping item, but found " + var6.getValue().size() + " items", var6.getStartMark());
            }

            Node var7 = ((NodeTuple)var6.getValue().get(0)).getKeyNode();
            Node var8 = ((NodeTuple)var6.getValue().get(0)).getValueNode();
            Object var9 = this.this$0.constructObject(var7);
            Object var10 = this.this$0.constructObject(var8);
            var2.put(var9, var10);
         }

         return var2;
      }
   }
}
