package paramorphism-obfuscator.verifierDisabler;

import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.lu;
import paramorphism-obfuscator.oj;
import paramorphism-obfuscator.xi;
import paramorphism-obfuscator.yl;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class SaveDisabler {
   @NotNull
   public static final ClassNode a(@Nullable lu var0) {
      AccessWrapper var10000 = PublicAccess.bln.a((AccessWrapper)yl.a());
      StringBuilder var10001 = (new StringBuilder()).append("dev/paramorphism/runtime/");
      String var10002;
      if (var0 != null) {
         var10002 = var0.a(-3, 0, oj.zf);
         if (var10002 != null) {
            return xi.a(var10000, var10001.append(var10002).toString(), 0, (String)null, (Function1)(new NativeLambda(var0)), 12, (Object)null);
         }
      }

      var10002 = "DisableBytecodeVerifier";
      return xi.a(var10000, var10001.append(var10002).toString(), 0, (String)null, (Function1)(new NativeLambda(var0)), 12, (Object)null);
   }

   public static ClassNode a(lu var0, int var1, Object var2) {
      if ((var1 & 1) != 0) {
         var0 = (lu)null;
      }

      return a(var0);
   }

   public static final void a() {
      ClassWriter var0 = new ClassWriter(1);
      a((lu)null, 1, (Object)null).accept((ClassVisitor)var0);
      Files.write(Paths.get("DisableBytecodeVerification.class"), var0.toByteArray(), new OpenOption[0]);
   }

   public static void main(String[] var0) {
      a();
   }
}
