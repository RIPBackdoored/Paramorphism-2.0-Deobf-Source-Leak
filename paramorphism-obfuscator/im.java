package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

public final class im extends Lambda implements Function1 {
   public static final im ni = new im();

   public Object invoke(Object var1) {
      return this.a((JsonNode)var1);
   }

   public final String a(JsonNode var1) {
      return var1.textValue();
   }

   public im() {
      super(1);
   }
}
