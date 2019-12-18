package paramorphism-obfuscator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import site.hackery.paramorphism.api.resources.ClassInfo;

public final class oz extends Lambda implements Function1 {
   public static final oz ban = new oz();

   public Object invoke(Object var1) {
      return this.a((ClassInfo)var1);
   }

   @NotNull
   public final Pair a(@NotNull ClassInfo var1) {
      String var2 = var1.component1();
      ClassNode var3 = var1.component2();
      Iterable var4 = (Iterable)var3.methods;
      boolean var5 = false;
      Collection var7 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10)));
      boolean var8 = false;
      Iterator var9 = var4.iterator();

      while(var9.hasNext()) {
         Object var10 = var9.next();
         MethodNode var11 = (MethodNode)var10;
         boolean var12 = false;
         String var17 = var11.name + var11.desc;
         var7.add(var17);
      }

      List var16 = (List)var7;
      return new Pair(var2, var16);
   }

   public oz() {
      super(1);
   }
}
