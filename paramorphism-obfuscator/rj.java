package paramorphism-obfuscator;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class rj extends Lambda implements Function1 {
   public final rn bex;
   public final Ref$IntRef bey;
   public final List bez;
   public final lu bfa;
   public final Map bfb;

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      ClassNode var2 = var1.d();
      rc var3 = new rc(this);
      Iterator var5 = var2.methods.iterator();

      while(var5.hasNext()) {
         MethodNode var4 = (MethodNode)var5.next();
         ListIterator var7 = var4.instructions.iterator();

         while(var7.hasNext()) {
            AbstractInsnNode var6 = (AbstractInsnNode)var7.next();
            if (var6 instanceof LdcInsnNode && ((LdcInsnNode)var6).cst instanceof String) {
               Object var10000 = ((LdcInsnNode)var6).cst;
               if (var10000 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
               }

               String var8 = (String)var10000;
               CharSequence var9 = (CharSequence)var8;
               boolean var10 = false;
               if (var9.length() != 0) {
                  Map var20 = this.bfb;
                  boolean var11 = false;
                  Object var12 = var20.get(var8);
                  boolean var13;
                  if (var12 == null) {
                     var13 = false;
                     ra var25 = var3.a();
                     var20.put(var8, var25);
                     var10000 = var25;
                  } else {
                     var10000 = var12;
                  }

                  ra var19 = (ra)var10000;
                  int var21 = this.bez.indexOf(var19);
                  pq var22 = new pq(var2.name, var4.name);
                  Map var23 = var19.b();
                  var13 = false;
                  Object var14 = var23.get(var8);
                  if (var14 == null) {
                     boolean var15 = false;
                     boolean var16 = false;
                     Set var28 = (Set)(new LinkedHashSet());
                     var23.put(var8, var28);
                     var10000 = var28;
                  } else {
                     var10000 = var14;
                  }

                  ((Set)var10000).add(var22);
                  var4.instructions.insertBefore(var6, (InsnList)xg.a((Function1)(new rk(this, var21, var19, var8))).getFirst());
                  var4.instructions.remove(var6);
                  var1.a(true);
                  byte var24 = 51;
                  int var26 = var2.version;
                  boolean var27 = false;
                  int var18 = Math.max(var24, var26);
                  var2.version = var18;
               }
            }
         }
      }

   }

   public rj(rn var1, Ref$IntRef var2, List var3, lu var4, Map var5) {
      super(1);
      this.bex = var1;
      this.bey = var2;
      this.bez = var3;
      this.bfa = var4;
      this.bfb = var5;
   }
}
