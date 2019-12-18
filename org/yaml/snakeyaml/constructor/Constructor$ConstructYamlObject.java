package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.Node;

public class Constructor$ConstructYamlObject implements Construct {
   final Constructor this$0;

   protected Constructor$ConstructYamlObject(Constructor var1) {
      super();
      this.this$0 = var1;
   }

   private Construct getConstructor(Node var1) {
      Class var2 = this.this$0.getClassForNode(var1);
      var1.setType(var2);
      Construct var3 = (Construct)this.this$0.yamlClassConstructors.get(var1.getNodeId());
      return var3;
   }

   public Object construct(Node var1) {
      Object var2 = null;

      try {
         var2 = this.getConstructor(var1).construct(var1);
      } catch (ConstructorException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new ConstructorException((String)null, (Mark)null, "Can't construct a java object for " + var1.getTag() + "; exception=" + var5.getMessage(), var1.getStartMark(), var5);
      }

      return var2;
   }

   public void construct2ndStep(Node var1, Object var2) {
      try {
         this.getConstructor(var1).construct2ndStep(var1, var2);
      } catch (Exception var4) {
         throw new ConstructorException((String)null, (Mark)null, "Can't construct a second step for a java object for " + var1.getTag() + "; exception=" + var4.getMessage(), var1.getStartMark(), var4);
      }

   }
}
