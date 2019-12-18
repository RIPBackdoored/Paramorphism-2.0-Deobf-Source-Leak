package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

public final class ka extends Lambda implements Function1 {
   public static final ka qt = new ka();

   public Object invoke(Object var1) {
      return this.a((JsonNode)var1);
   }

   public final String a(JsonNode var1) {
      return var1.textValue();
   }

   public ka() {
      super(1);
   }
}
