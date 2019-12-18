package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Triple;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import site.hackery.paramorphism.api.resources.ClassSet;

public final class pg {
   @NotNull
   public static final Map a(@NotNull oh var0, @NotNull ClassSet var1, @NotNull lc var2) {
      Sequence var3 = a(var1);
      boolean var5 = false;
      Map var4 = (Map)(new LinkedHashMap());
      Iterator var6 = var3.iterator();

      while(var6.hasNext()) {
         InvokeDynamicInsnNode var11 = (InvokeDynamicInsnNode)var6.next();
         if (Intrinsics.areEqual((Object)var11.bsm.getOwner(), (Object)"java/lang/invoke/LambdaMetafactory") && Intrinsics.areEqual((Object)var11.bsm.getName(), (Object)"metafactory")) {
            String var7 = var11.name;
            String var8 = var11.bsmArgs[0].toString();
            String var9 = Type.getReturnType(var11.desc).getInternalName();
            le var10 = var2.a(var9);
            if (var10 != null) {
               var4.put(var11.name + ' ' + var11.desc, new Triple(var10.a(), var7, var8));
            }
         }
      }

      return var4;
   }

   private static final Sequence a(ClassSet var0) {
      return SequencesKt.map(SequencesKt.filter(SequencesKt.flatMap(SequencesKt.flatMap(SequencesKt.map(var0.all(), (Function1)pe.bax), (Function1)pi.bba), (Function1)pd.baw), (Function1)pf.bay), (Function1)ph.baz);
   }
}
