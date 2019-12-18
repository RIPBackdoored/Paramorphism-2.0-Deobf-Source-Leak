package org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

public class JSRInlinerAdapter extends MethodNode implements Opcodes {
   private final BitSet mainSubroutineInsns;
   private final Map subroutinesInsns;
   final BitSet sharedSubroutineInsns;

   public JSRInlinerAdapter(MethodVisitor var1, int var2, String var3, String var4, String var5, String[] var6) {
      this(458752, var1, var2, var3, var4, var5, var6);
      if (this.getClass() != JSRInlinerAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected JSRInlinerAdapter(int var1, MethodVisitor var2, int var3, String var4, String var5, String var6, String[] var7) {
      super(var1, var3, var4, var5, var6, var7);
      this.mainSubroutineInsns = new BitSet();
      this.subroutinesInsns = new HashMap();
      this.sharedSubroutineInsns = new BitSet();
      this.mv = var2;
   }

   public void visitJumpInsn(int var1, Label var2) {
      super.visitJumpInsn(var1, var2);
      LabelNode var3 = ((JumpInsnNode)this.instructions.getLast()).label;
      if (var1 == 168 && !this.subroutinesInsns.containsKey(var3)) {
         this.subroutinesInsns.put(var3, new BitSet());
      }

   }

   public void visitEnd() {
      if (!this.subroutinesInsns.isEmpty()) {
         this.findSubroutinesInsns();
         this.emitCode();
      }

      if (this.mv != null) {
         this.accept(this.mv);
      }

   }

   private void findSubroutinesInsns() {
      BitSet var1 = new BitSet();
      this.findSubroutineInsns(0, this.mainSubroutineInsns, var1);
      Iterator var2 = this.subroutinesInsns.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         LabelNode var4 = (LabelNode)var3.getKey();
         BitSet var5 = (BitSet)var3.getValue();
         this.findSubroutineInsns(this.instructions.indexOf(var4), var5, var1);
      }

   }

   private void findSubroutineInsns(int var1, BitSet var2, BitSet var3) {
      this.findReachableInsns(var1, var2, var3);

      boolean var4;
      do {
         var4 = false;
         Iterator var5 = this.tryCatchBlocks.iterator();

         while(var5.hasNext()) {
            TryCatchBlockNode var6 = (TryCatchBlockNode)var5.next();
            int var7 = this.instructions.indexOf(var6.handler);
            if (!var2.get(var7)) {
               int var8 = this.instructions.indexOf(var6.start);
               int var9 = this.instructions.indexOf(var6.end);
               int var10 = var2.nextSetBit(var8);
               if (var10 >= var8 && var10 < var9) {
                  this.findReachableInsns(var7, var2, var3);
                  var4 = true;
               }
            }
         }
      } while(var4);

   }

   private void findReachableInsns(int var1, BitSet var2, BitSet var3) {
      int var4 = var1;

      while(var4 < this.instructions.size()) {
         if (var2.get(var4)) {
            return;
         }

         var2.set(var4);
         if (var3.get(var4)) {
            this.sharedSubroutineInsns.set(var4);
         }

         var3.set(var4);
         AbstractInsnNode var5 = this.instructions.get(var4);
         if (var5.getType() == 7 && var5.getOpcode() != 168) {
            JumpInsnNode var10 = (JumpInsnNode)var5;
            this.findReachableInsns(this.instructions.indexOf(var10.label), var2, var3);
         } else {
            Iterator var7;
            LabelNode var8;
            if (var5.getType() == 11) {
               TableSwitchInsnNode var9 = (TableSwitchInsnNode)var5;
               this.findReachableInsns(this.instructions.indexOf(var9.dflt), var2, var3);
               var7 = var9.labels.iterator();

               while(var7.hasNext()) {
                  var8 = (LabelNode)var7.next();
                  this.findReachableInsns(this.instructions.indexOf(var8), var2, var3);
               }
            } else if (var5.getType() == 12) {
               LookupSwitchInsnNode var6 = (LookupSwitchInsnNode)var5;
               this.findReachableInsns(this.instructions.indexOf(var6.dflt), var2, var3);
               var7 = var6.labels.iterator();

               while(var7.hasNext()) {
                  var8 = (LabelNode)var7.next();
                  this.findReachableInsns(this.instructions.indexOf(var8), var2, var3);
               }
            }
         }

         switch(this.instructions.get(var4).getOpcode()) {
         case 167:
         case 169:
         case 170:
         case 171:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 191:
            return;
         case 168:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 186:
         case 187:
         case 188:
         case 189:
         case 190:
         default:
            ++var4;
         }
      }

   }

   private void emitCode() {
      LinkedList var1 = new LinkedList();
      var1.add(new JSRInlinerAdapter$Instantiation(this, (JSRInlinerAdapter$Instantiation)null, this.mainSubroutineInsns));
      InsnList var2 = new InsnList();
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();

      while(!var1.isEmpty()) {
         JSRInlinerAdapter$Instantiation var5 = (JSRInlinerAdapter$Instantiation)var1.removeFirst();
         this.emitInstantiation(var5, var1, var2, var3, var4);
      }

      this.instructions = var2;
      this.tryCatchBlocks = var3;
      this.localVariables = var4;
   }

   private void emitInstantiation(JSRInlinerAdapter$Instantiation var1, List var2, InsnList var3, List var4, List var5) {
      LabelNode var6 = null;

      LabelNode var9;
      LabelNode var17;
      for(int var7 = 0; var7 < this.instructions.size(); ++var7) {
         AbstractInsnNode var8 = this.instructions.get(var7);
         if (var8.getType() == 8) {
            var9 = (LabelNode)var8;
            var17 = var1.getClonedLabel(var9);
            if (var17 != var6) {
               var3.add((AbstractInsnNode)var17);
               var6 = var17;
            }
         } else if (var1.findOwner(var7) == var1) {
            if (var8.getOpcode() != 169) {
               if (var8.getOpcode() == 168) {
                  var9 = ((JumpInsnNode)var8).label;
                  BitSet var16 = (BitSet)this.subroutinesInsns.get(var9);
                  JSRInlinerAdapter$Instantiation var11 = new JSRInlinerAdapter$Instantiation(this, var1, var16);
                  LabelNode var12 = var11.getClonedLabelForJumpInsn(var9);
                  var3.add((AbstractInsnNode)(new InsnNode(Opcodes.ACONST_NULL)));
                  var3.add((AbstractInsnNode)(new JumpInsnNode(Opcodes.GOTO, var12)));
                  var3.add((AbstractInsnNode)var11.returnLabel);
                  var2.add(var11);
               } else {
                  var3.add(var8.clone(var1));
               }
            } else {
               var9 = null;

               for(JSRInlinerAdapter$Instantiation var10 = var1; var10 != null; var10 = var10.parent) {
                  if (var10.subroutineInsns.get(var7)) {
                     var9 = var10.returnLabel;
                  }
               }

               if (var9 == null) {
                  throw new IllegalArgumentException("Instruction #" + var7 + " is a RET not owned by any subroutine");
               }

               var3.add((AbstractInsnNode)(new JumpInsnNode(Opcodes.GOTO, var9)));
            }
         }
      }

      Iterator var13 = this.tryCatchBlocks.iterator();

      while(true) {
         TryCatchBlockNode var14;
         do {
            if (!var13.hasNext()) {
               var13 = this.localVariables.iterator();

               while(var13.hasNext()) {
                  LocalVariableNode var15 = (LocalVariableNode)var13.next();
                  var9 = var1.getClonedLabel(var15.start);
                  var17 = var1.getClonedLabel(var15.end);
                  if (var9 != var17) {
                     var5.add(new LocalVariableNode(var15.name, var15.desc, var15.signature, var9, var17, var15.index));
                  }
               }

               return;
            }

            var14 = (TryCatchBlockNode)var13.next();
            var9 = var1.getClonedLabel(var14.start);
            var17 = var1.getClonedLabel(var14.end);
         } while(var9 == var17);

         LabelNode var18 = var1.getClonedLabelForJumpInsn(var14.handler);
         if (var9 == null || var17 == null || var18 == null) {
            throw new AssertionError("Internal error!");
         }

         var4.add(new TryCatchBlockNode(var9, var17, var18, var14.type));
      }
   }
}
