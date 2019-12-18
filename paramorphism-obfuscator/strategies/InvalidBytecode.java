package paramorphism-obfuscator.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import paramorphism-obfuscator.kg;
import paramorphism-obfuscator.md;
import paramorphism-obfuscator.xg;
import paramorphism-obfuscator.verifierDisabler.CreatePriorityClass;

public final class InvalidBytecode extends Lambda implements Function1 {
   public final kg bhc;

   public Object invoke(Object var1) {
      this.a((md)var1);
      return Unit.INSTANCE;
   }

   public final void a(@NotNull md var1) {
      ClassNode var2 = var1.d();
      if (!var2.methods.isEmpty()) {
         Iterator var4 = var2.methods.iterator();

         while(true) {
            MethodNode var3;
            do {
               if (!var4.hasNext()) {
                  if ((var2.access & 512) == 0) {
                     List var27 = var2.methods;
                     var3 = new MethodNode(4170, "_protected_by_paramorphism", "(L ;)V", (String)null, (String[])null);
                     byte var19 = 0;
                     List var28 = var27;
                     boolean var21 = false;
                     boolean var22 = false;
                     boolean var23 = false;
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(200)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(Opcodes.LAND)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(255)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(255)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(255)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(Opcodes.BIPUSH)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(Opcodes.NOP)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(Opcodes.POP)));
                     var3.instructions.add((AbstractInsnNode)(new InsnNode(Opcodes.RETURN)));
                     var28.add(var19, var3);
                  }

                  return;
               }

               var3 = (MethodNode)var4.next();
            } while(var3.instructions.size() == 0);

            var3.instructions.insert((InsnList)xg.a((Function1)CreatePriorityClass.bgv).getFirst());
            Random var5 = this.bhc.h().a();
            int var6 = 0;

            for(byte var7 = 2; var6 < var7; ++var6) {
               LabelNode var8 = new LabelNode();
               var3.instructions.insert((AbstractInsnNode)var8);
               boolean var10 = false;
               List var9 = (List)(new ArrayList());
               boolean var11 = false;
               List var24 = (List)(new ArrayList());
               ListIterator var12 = var3.instructions.iterator();

               while(var12.hasNext()) {
                  AbstractInsnNode var25 = (AbstractInsnNode)var12.next();
                  if (var25 instanceof JumpInsnNode) {
                     var9.add(((JumpInsnNode)var25).label);
                     JumpInsnNode var10000 = (JumpInsnNode)var25;
                     LabelNode var13 = new LabelNode();
                     JumpInsnNode var18 = var10000;
                     boolean var14 = false;
                     boolean var15 = false;
                     boolean var17 = false;
                     var24.add(var13);
                     var18.label = var13;
                  }
               }

               Pair var26 = xg.a((Function1)(new JumpReplacer(var8, var24, var9)));
               var3.instructions.insert((InsnList)var26.getFirst());
               var3.tryCatchBlocks.addAll((Collection)var26.getSecond());
            }

            var1.a(true);
         }
      }
   }

   public InvalidBytecode(kg var1) {
      super(1);
      this.bhc = var1;
   }
}
