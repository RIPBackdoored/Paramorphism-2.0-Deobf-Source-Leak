package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class SafeRepresenter$RepresentByteArray implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentByteArray(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      char[] var2 = Base64Coder.encode((byte[])((byte[])var1));
      return this.this$0.representScalar(Tag.BINARY, String.valueOf(var2), '|');
   }
}
