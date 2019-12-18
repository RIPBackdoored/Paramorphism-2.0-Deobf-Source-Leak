package paramorphism-obfuscator;

import java.util.zip.ZipEntry;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

public final class th extends Lambda implements Function1 {
   public final tc bhz;

   public Object invoke(Object var1) {
      return this.a((ZipEntry)var1);
   }

   @NotNull
   public final Pair a(ZipEntry var1) {
      return new Pair(var1.getName(), tc.a(this.bhz).getInputStream(var1));
   }

   public th(tc var1) {
      super(1);
      this.bhz = var1;
   }
}
