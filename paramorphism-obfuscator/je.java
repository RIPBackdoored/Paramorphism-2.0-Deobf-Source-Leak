package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class je extends Lambda implements Function1 {
   public final ir oj;

   public Object invoke(Object var1) {
      return this.a((JsonNode)var1);
   }

   @NotNull
   public final File a(JsonNode var1) {
      return this.oj.nn.d(var1.textValue());
   }

   public je(ir var1) {
      super(1);
      this.oj = var1;
   }
}
