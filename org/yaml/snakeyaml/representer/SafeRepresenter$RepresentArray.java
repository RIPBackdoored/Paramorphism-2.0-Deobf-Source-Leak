package org.yaml.snakeyaml.representer;

import java.util.Arrays;
import java.util.List;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentArray implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentArray(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      Object[] var2 = (Object[])((Object[])var1);
      List var3 = Arrays.asList(var2);
      return this.this$0.representSequence(Tag.SEQ, var3, (Boolean)null);
   }
}
