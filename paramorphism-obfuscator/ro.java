package paramorphism-obfuscator;

import java.util.List;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class ro {
   @NotNull
   public static final ClassNode a(@NotNull String var0, @NotNull List var1, @NotNull lu var2) {
      return xi.a((AccessWrapper)PublicAccess.bln, var0, 52, (String)null, (Function1)(new rv(var1, var2)), 8, (Object)null);
   }
}
