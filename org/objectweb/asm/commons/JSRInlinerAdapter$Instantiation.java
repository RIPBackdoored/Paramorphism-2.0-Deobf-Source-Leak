package org.objectweb.asm.commons;

import java.util.AbstractMap;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

class JSRInlinerAdapter$Instantiation extends AbstractMap {
   final JSRInlinerAdapter$Instantiation parent;
   final BitSet subroutineInsns;
   final Map clonedLabels;
   final LabelNode returnLabel;
   final JSRInlinerAdapter this$0;

   JSRInlinerAdapter$Instantiation(JSRInlinerAdapter var1, JSRInlinerAdapter$Instantiation var2, BitSet var3) {
      super();
      this.this$0 = var1;

      for(JSRInlinerAdapter$Instantiation var4 = var2; var4 != null; var4 = var4.parent) {
         if (var4.subroutineInsns == var3) {
            throw new IllegalArgumentException("Recursive invocation of " + var3);
         }
      }

      this.parent = var2;
      this.subroutineInsns = var3;
      this.returnLabel = var2 == null ? null : new LabelNode();
      this.clonedLabels = new HashMap();
      LabelNode var8 = null;

      for(int var5 = 0; var5 < var1.instructions.size(); ++var5) {
         AbstractInsnNode var6 = var1.instructions.get(var5);
         if (var6.getType() == 8) {
            LabelNode var7 = (LabelNode)var6;
            if (var8 == null) {
               var8 = new LabelNode();
            }

            this.clonedLabels.put(var7, var8);
         } else if (this.findOwner(var5) == this) {
            var8 = null;
         }
      }

   }

   JSRInlinerAdapter$Instantiation findOwner(int var1) {
      if (!this.subroutineInsns.get(var1)) {
         return null;
      } else if (!this.this$0.sharedSubroutineInsns.get(var1)) {
         return this;
      } else {
         JSRInlinerAdapter$Instantiation var2 = this;

         for(JSRInlinerAdapter$Instantiation var3 = this.parent; var3 != null; var3 = var3.parent) {
            if (var3.subroutineInsns.get(var1)) {
               var2 = var3;
            }
         }

         return var2;
      }
   }

   LabelNode getClonedLabelForJumpInsn(LabelNode var1) {
      return (LabelNode)this.findOwner(this.this$0.instructions.indexOf(var1)).clonedLabels.get(var1);
   }

   LabelNode getClonedLabel(LabelNode var1) {
      return (LabelNode)this.clonedLabels.get(var1);
   }

   public Set entrySet() {
      throw new UnsupportedOperationException();
   }

   public LabelNode get(Object var1) {
      return this.getClonedLabelForJumpInsn((LabelNode)var1);
   }

   public boolean equals(Object var1) {
      throw new UnsupportedOperationException();
   }

   public int hashCode() {
      throw new UnsupportedOperationException();
   }

   public Object get(Object var1) {
      return this.get(var1);
   }
}
