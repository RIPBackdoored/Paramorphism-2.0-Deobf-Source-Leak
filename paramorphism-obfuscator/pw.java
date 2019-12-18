package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.access.StaticAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class pw {
   private static final int bbz = 170;

   private static final Pair a(String var0, ClassNode var1, List var2, String var3) {
      boolean var5 = false;
      List var4 = (List)(new ArrayList());
      ClassNode var6 = xi.a(PublicAccess.bln.a((AccessWrapper)StaticAccess.blo), var0, 52, (String)null, (Function1)(new px(var1, var3, var2, var4)), 8, (Object)null);
      return new Pair(var6, var4);
   }

   private static final ClassNode a(qm var0, int var1, String var2, String var3) {
      return xi.a((AccessWrapper)PublicAccess.bln, var2 + '$' + var1, 0, (String)null, (Function1)(new qb(var2, var0, var3)), 12, (Object)null);
   }

   public static final Pair b(String var0, ClassNode var1, List var2, String var3) {
      return a(var0, var1, var2, var3);
   }

   public static final ClassNode b(qm var0, int var1, String var2, String var3) {
      return a(var0, var1, var2, var3);
   }
}
