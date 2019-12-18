package paramorphism-obfuscator;

import java.io.File;
import java.io.FileFilter;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ug implements FileFilter {
   public static final ug bjf = new ug();

   public final boolean accept(@NotNull File var1) {
      return Intrinsics.areEqual((Object)FilesKt.getExtension(var1), (Object)"jar");
   }

   public ug() {
      super();
   }
}
