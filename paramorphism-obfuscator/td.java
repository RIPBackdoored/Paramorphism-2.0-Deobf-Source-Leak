package paramorphism-obfuscator;

import java.util.zip.ZipEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

public final class td extends Lambda implements Function1 {
   public static final td bhv = new td();

   public Object invoke(Object var1) {
      return this.a((ZipEntry)var1);
   }

   public final String a(ZipEntry var1) {
      return var1.getName();
   }

   public td() {
      super(1);
   }
}
