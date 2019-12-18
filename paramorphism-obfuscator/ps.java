package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ps extends Lambda implements Function1 {
   public final qc bbm;
   public final Ref$IntRef bbn;
   public final List bbo;
   public final lu bbp;
   public final Map bbq;

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      ClassNode var2 = var1.d();
      pz var3 = new pz(this);
      Iterator var5 = var2.methods.iterator();

      while(var5.hasNext()) {
         MethodNode var4 = (MethodNode)var5.next();
         ListIterator var7 = var4.instructions.iterator();

         while(var7.hasNext()) {
            AbstractInsnNode var6 = (AbstractInsnNode)var7.next();
            if (var6 instanceof MethodInsnNode && ((MethodInsnNode)var6).getOpcode() != 183 && ((MethodInsnNode)var6).getOpcode() == 184) {
               pt var8 = new pt(((MethodInsnNode)var6).owner, ((MethodInsnNode)var6).name, ((MethodInsnNode)var6).desc);
               Map var10 = this.bbq;
               boolean var11 = false;
               Object var12 = var10.get(var8);
               Object var10000;
               if (var12 == null) {
                  boolean var13 = false;
                  qm var24 = var3.a();
                  var10.put(var8, var24);
                  var10000 = var24;
               } else {
                  var10000 = var12;
               }

               qm var9 = (qm)var10000;
               int var20 = this.bbo.indexOf(var9);
               Map var22 = var9.b();
               pq var25 = new pq(var2.name, var4.name);
               boolean var14 = false;
               Object var15 = var22.get(var25);
               if (var15 == null) {
                  boolean var16 = false;
                  boolean var17 = false;
                  Set var27 = (Set)(new LinkedHashSet());
                  var22.put(var25, var27);
                  var10000 = var27;
               } else {
                  var10000 = var15;
               }

               Set var21 = (Set)var10000;
               var21.add(var8);
               var4.instructions.insertBefore(var6, (InsnList)xg.a((Function1)(new qj(this, var20, var21, var8, var6))).getFirst());
               var4.instructions.remove(var6);
               var1.a(true);
               byte var23 = 51;
               int var26 = var2.version;
               var14 = false;
               int var19 = Math.max(var23, var26);
               var2.version = var19;
            }
         }
      }

   }

   public ps(qc var1, Ref$IntRef var2, List var3, lu var4, Map var5) {
      super(1);
      this.bbm = var1;
      this.bbn = var2;
      this.bbo = var3;
      this.bbp = var4;
      this.bbq = var5;
   }
}
