package paramorphism-obfuscator;

import java.util.Collection;
import java.util.Iterator;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class pk extends Lambda implements Function1 {
   public static final pk bbc = new pk();

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      Iterable var3 = (Iterable)var1.d().fields;
      boolean var4 = false;
      boolean var10000;
      if (var3 instanceof Collection && ((Collection)var3).isEmpty()) {
         var10000 = false;
      } else {
         Iterator var5 = var3.iterator();

         while(true) {
            if (!var5.hasNext()) {
               var10000 = false;
               break;
            }

            Object var6 = var5.next();
            FieldNode var7 = (FieldNode)var6;
            boolean var8 = false;
            if (Intrinsics.areEqual((Object)var7.name, (Object)"$$delegatedProperties") && Intrinsics.areEqual((Object)var7.desc, (Object)"[Lkotlin/reflect/KProperty;")) {
               var10000 = true;
               break;
            }
         }
      }

      boolean var2 = var10000;
      if (var2) {
         Iterable var11 = (Iterable)var1.d().methods;
         boolean var13 = false;
         Iterator var15 = var11.iterator();

         Object var18;
         while(true) {
            if (!var15.hasNext()) {
               var18 = null;
               break;
            }

            Object var16 = var15.next();
            MethodNode var17 = (MethodNode)var16;
            boolean var9 = false;
            if (Intrinsics.areEqual((Object)var17.name, (Object)"<clinit>") && Intrinsics.areEqual((Object)var17.desc, (Object)"()V")) {
               var18 = var16;
               break;
            }
         }

         MethodNode var19 = (MethodNode)var18;
         if (var19 != null) {
            MethodNode var10 = var19;
            Sequence var12 = lq.a(var10.instructions, new lr[]{(lr)(new lo(89)), (lr)ln.ua, (lr)(new ll(187, Primitives.getType("kotlin/jvm/internal/PropertyReference1Impl"))), (lr)(new lo(89)), (lr)(new lo(18)), (lr)(new lo(184)), (lr)(new lo(18)), (lr)(new lo(18)), (lr)(new lo(183)), (lr)(new lo(184)), (lr)(new lo(192)), (lr)(new lo(83))});
            var15 = var12.iterator();

            while(var15.hasNext()) {
               AbstractInsnNode[] var14 = (AbstractInsnNode[])var15.next();
               AbstractInsnNode var20 = var14[6];
               if (var14[6] == null) {
                  throw new TypeCastException("null cannot be cast to non-null type org.objectweb.asm.tree.LdcInsnNode");
               }

               ((LdcInsnNode)var20).cst = "[Hidden]";
               var20 = var14[7];
               if (var14[7] == null) {
                  throw new TypeCastException("null cannot be cast to non-null type org.objectweb.asm.tree.LdcInsnNode");
               }

               ((LdcInsnNode)var20).cst = "[Hidden]";
               var1.a(true);
            }

         }
      }
   }

   public pk() {
      super(1);
   }
}
