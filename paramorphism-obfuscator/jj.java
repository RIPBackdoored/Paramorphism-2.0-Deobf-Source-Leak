package paramorphism-obfuscator;

import com.fasterxml.jackson.databind.JsonNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

public final class jj extends Lambda implements Function0 {
   public final ja pl;

   public Object invoke() {
      return this.a();
   }

   @Nullable
   public final String a() {
      JsonNode var10000 = this.pl.c().e(this.pl.b() + ".prefix");
      return var10000 != null ? var10000.textValue() : null;
   }

   public jj(ja var1) {
      super(0);
      this.pl = var1;
   }
}
