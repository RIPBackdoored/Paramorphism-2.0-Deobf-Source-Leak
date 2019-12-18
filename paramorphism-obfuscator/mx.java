package paramorphism-obfuscator;

import java.util.Map;
import java.util.jar.Attributes.Name;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class mx extends Lambda implements Function1 {
   public final mz vx;
   public final kg vy;
   public final nd vz;

   public Object invoke(Object var1) {
      this.a((mg)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull mg var1) {
      Object var10000 = this.vy.g().getMainAttributes().get(Name.MAIN_CLASS);
      if (!(var10000 instanceof String)) {
         var10000 = null;
      }

      String var6 = (String)var10000;
      if (var6 != null) {
         String var2 = var6;
         ClassNode var5 = xi.a((AccessWrapper)PublicAccess.bln, "dev/paramorphism/runtime/PackedClassLoader", 0, "java/lang/ClassLoader", (Function1)(new nb(this, var2)), 4, (Object)null);
         this.vy.c().put(new ClassInfo(var5.name, var5, 0, 4, (DefaultConstructorMarker)null));
         ((Map)this.vy.g().getMainAttributes()).put(Name.MAIN_CLASS, StringsKt.replace$default(var5.name, '/', '.', false, 4, (Object)null));
      } else {
         String var3 = "Could not find value of Main-Class attribute";
         boolean var4 = false;
         throw (Throwable)(new IllegalStateException(var3.toString()));
      }
   }

   public mx(mz var1, kg var2, nd var3) {
      super(1);
      this.vx = var1;
      this.vy = var2;
      this.vz = var3;
   }
}
