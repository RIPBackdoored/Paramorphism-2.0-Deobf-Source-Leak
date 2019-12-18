package paramorphism-obfuscator;

import java.util.zip.ZipEntry;
import org.jetbrains.annotations.NotNull;
import site.hackery.paramorphism.api.resources.Resource;

public final class ma {
   @NotNull
   private ZipEntry ul;
   @NotNull
   private final String um;
   @NotNull
   private final Resource un;

   @NotNull
   public final ZipEntry a() {
      return this.ul;
   }

   public final void a(@NotNull ZipEntry var1) {
      this.ul = var1;
   }

   @NotNull
   public final String b() {
      return this.um;
   }

   @NotNull
   public final Resource c() {
      return this.un;
   }

   public ma(@NotNull ZipEntry var1, @NotNull String var2, @NotNull Resource var3) {
      super();
      this.ul = var1;
      this.um = var2;
      this.un = var3;
   }
}
