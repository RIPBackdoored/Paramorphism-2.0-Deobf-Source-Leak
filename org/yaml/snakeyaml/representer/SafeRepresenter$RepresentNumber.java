package org.yaml.snakeyaml.representer;

import java.math.BigInteger;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentNumber implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentNumber(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      Tag var2;
      String var3;
      if (!(var1 instanceof Byte) && !(var1 instanceof Short) && !(var1 instanceof Integer) && !(var1 instanceof Long) && !(var1 instanceof BigInteger)) {
         Number var4 = (Number)var1;
         var2 = Tag.FLOAT;
         if (var4.equals(Double.NaN)) {
            var3 = ".NaN";
         } else if (var4.equals(Double.POSITIVE_INFINITY)) {
            var3 = ".inf";
         } else if (var4.equals(Double.NEGATIVE_INFINITY)) {
            var3 = "-.inf";
         } else {
            var3 = var4.toString();
         }
      } else {
         var2 = Tag.INT;
         var3 = var1.toString();
      }

      return this.this$0.representScalar(this.this$0.getTag(var1.getClass(), var2), var3);
   }
}
