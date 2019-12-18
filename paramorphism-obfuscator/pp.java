package paramorphism-obfuscator;

import java.util.Iterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class pp extends Lambda implements Function1 {
   public static final pp bbh = new pp();

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      Iterator var3 = var1.d().methods.iterator();

      label104:
      while(var3.hasNext()) {
         MethodNode var2 = (MethodNode)var3.next();
         Iterator var6 = CollectionsKt.withIndex((Iterator)var2.instructions.iterator());
         boolean var7 = false;
         Iterator var5 = var6;

         while(true) {
            int var16;
            AbstractInsnNode var17;
            do {
               do {
                  do {
                     if (!var5.hasNext()) {
                        continue label104;
                     }

                     IndexedValue var4 = (IndexedValue)var5.next();
                     var16 = var4.component1();
                     var17 = (AbstractInsnNode)var4.component2();
                  } while(!(var17 instanceof MethodInsnNode));
               } while((Intrinsics.areEqual((Object)((MethodInsnNode)var17).owner, (Object)"kotlin/jvm/internal/Intrinsics") ^ 1) != 0);
            } while(!StringsKt.contains$default((CharSequence)((MethodInsnNode)var17).desc, (CharSequence)"Ljava/lang/String;", false, 2, (Object)null));

            Type[] var8 = Type.getArgumentTypes(((MethodInsnNode)var17).desc);
            Type[] var10 = var8;
            boolean var11 = false;
            int var12 = var8.length;
            --var12;
            boolean var13 = false;

            int var10000;
            while(true) {
               if (var12 < 0) {
                  var10000 = -1;
                  break;
               }

               Type var14 = var10[var12];
               boolean var15 = false;
               if (Intrinsics.areEqual((Object)var14.getInternalName(), (Object)"java/lang/String")) {
                  var10000 = var12;
                  break;
               }

               --var12;
            }

            int var9 = var10000;
            int var18 = var8.length - var9;
            AbstractInsnNode var19 = var2.instructions.get(var16 - var18);
            if (!(var19 instanceof LdcInsnNode)) {
               lh.tv.b("[Kotlin Intrinsics Concealing] Warning:Estimated string constant load instruction was not 'LDC' - Is the Kotlin compiler being weird?");
            } else {
               ((LdcInsnNode)var19).cst = "[Hidden]";
            }
         }
      }

   }

   public pp() {
      super(1);
   }
}
