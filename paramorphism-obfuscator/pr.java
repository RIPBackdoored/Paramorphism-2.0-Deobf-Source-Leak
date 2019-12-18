package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import paramorphism-obfuscator.access.AbstractAccess;
import paramorphism-obfuscator.access.PublicAccess;
import paramorphism-obfuscator.wrappers.AccessWrapper;

public final class pr extends Lambda implements Function1 {
   public final qc bbk;
   public final List bbl;

   public Object invoke(Object var1) {
      this.a((ClassInfoList)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassInfoList var1) {
      String var2 = "+";
      ClassNode var3 = xi.a(PublicAccess.bln.a((AccessWrapper)AbstractAccess.bkz).addAccess((AccessWrapper)yl.b()), qc.b(this.bbk) + '⛔', 0, (String)null, (Function1)(new qf(var2)), 12, (Object)null);
      var1.addClassNode(var3);
      Pair var6 = pw.b(qc.c(this.bbk).getInternalName(), var3, this.bbl, var2);
      ClassNode var4 = (ClassNode)var6.component1();
      List var5 = (List)var6.component2();
      var1.addClassNode(var4);
      Iterable var12 = (Iterable)var5;
      boolean var7 = false;
      Iterator var8 = var12.iterator();

      while(var8.hasNext()) {
         Object var9 = var8.next();
         ClassNode var10 = (ClassNode)var9;
         boolean var11 = false;
         var1.addClassNode(var10);
      }

   }

   public pr(qc var1, List var2) {
      super(1);
      this.bbk = var1;
      this.bbl = var2;
   }
}
