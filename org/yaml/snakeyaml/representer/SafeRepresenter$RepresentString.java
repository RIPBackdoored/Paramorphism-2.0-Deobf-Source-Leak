package org.yaml.snakeyaml.representer;

import java.io.UnsupportedEncodingException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.StreamReader;

public class SafeRepresenter$RepresentString implements Represent {
   final SafeRepresenter this$0;

   protected SafeRepresenter$RepresentString(SafeRepresenter var1) {
      super();
      this.this$0 = var1;
   }

   public Node representData(Object var1) {
      Tag var2 = Tag.STR;
      Character var3 = null;
      String var4 = var1.toString();
      if (!StreamReader.isPrintable(var4)) {
         var2 = Tag.BINARY;

         char[] var5;
         try {
            byte[] var6 = var4.getBytes("UTF-8");
            String var7 = new String(var6, "UTF-8");
            if (!var7.equals(var4)) {
               throw new YAMLException("invalid string value has occurred");
            }

            var5 = Base64Coder.encode(var6);
         } catch (UnsupportedEncodingException var8) {
            throw new YAMLException(var8);
         }

         var4 = String.valueOf(var5);
         var3 = '|';
      }

      if (this.this$0.defaultScalarStyle == null && SafeRepresenter.MULTILINE_PATTERN.matcher(var4).find()) {
         var3 = '|';
      }

      return this.this$0.representScalar(var2, var4, var3);
   }
}
