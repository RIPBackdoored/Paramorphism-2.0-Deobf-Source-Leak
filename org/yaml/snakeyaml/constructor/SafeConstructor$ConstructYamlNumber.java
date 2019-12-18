package org.yaml.snakeyaml.constructor;

import java.text.NumberFormat;
import java.text.ParseException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeConstructor$ConstructYamlNumber extends AbstractConstruct {
   private final NumberFormat nf;
   final SafeConstructor this$0;

   public SafeConstructor$ConstructYamlNumber(SafeConstructor var1) {
      super();
      this.this$0 = var1;
      this.nf = NumberFormat.getInstance();
   }

   public Object construct(Node var1) {
      ScalarNode var2 = (ScalarNode)var1;

      Number var10000;
      try {
         var10000 = this.nf.parse(var2.getValue());
      } catch (ParseException var5) {
         String var4 = var2.getValue().toLowerCase();
         if (!var4.contains("inf") && !var4.contains("nan")) {
            throw new IllegalArgumentException("Unable to parse as Number: " + var2.getValue());
         }

         return ((Construct)this.this$0.yamlConstructors.get(Tag.FLOAT)).construct(var1);
      }

      return var10000;
   }
}
