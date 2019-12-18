package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.wrappers.AccessWrapper;
import paramorphism-obfuscator.wrappers.ClassWrapper;

public final class ru extends Lambda implements Function1 {
   public final ra bfz;
   public final lu bga;

   public Object invoke(Object var1) {
      this.a((ClassWrapper)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull ClassWrapper var1) {
      boolean var3 = false;
      List var2 = (List)(new ArrayList());
      MethodNode var15 = qz.b(var1);
      Map var6 = this.bfz.b();
      boolean var7 = false;
      Iterator var19 = CollectionsKt.withIndex(var6.entrySet().iterator());
      var7 = false;
      Iterator var5 = var19;

      while(var5.hasNext()) {
         IndexedValue var4 = (IndexedValue)var5.next();
         int var20 = var4.component1();
         Entry var21 = (Entry)var4.component2();
         String var10000 = this.bga.a(-1, var20, oj.zf);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         String var8 = var10000;
         boolean var13 = false;
         String var9 = (String)var21.getKey();
         var13 = false;
         Set var10 = (Set)var21.getValue();
         double var12 = (double)var9.length() / (double)5;
         boolean var14 = false;
         int var11 = (int)Math.ceil(var12);
         qz.c(var1, var8);
         Pair var25 = var9.length() >= 8192 ? new Pair(MapsKt.mapOf(TuplesKt.to(0, qz.b(var9, 0))), rf.beu) : new Pair(qz.b(var9, var10), qy.bef);
         Map var23 = (Map)var25.component1();
         Function1 var24 = (Function1)var25.component2();
         FieldNode var26 = qz.d(var1, var8);
         qz.b(var1, var9, var8, var26, var24);
         var2.add(qz.b(var1, var8, var15, var11, var10, var23));
      }

      Collection var16 = (Collection)var2;
      boolean var18 = false;
      if (!var16.isEmpty()) {
         AccessWrapper var10001 = (AccessWrapper)var1.h();
         Type var10003 = var1.be();
         Function1 var10004 = (Function1)(new rm(var2));
         Object[] var17 = new Object[0];
         var5 = null;
         var6 = null;
         Function1 var22 = var10004;
         ClassWrapper.addMethod(var1, var10001, "<clinit>", var10003, var17, var6, var5, var22, 48, (Object)null);
      }

   }

   public ru(ra var1, lu var2) {
      super(1);
      this.bfz = var1;
      this.bga = var2;
   }
}
