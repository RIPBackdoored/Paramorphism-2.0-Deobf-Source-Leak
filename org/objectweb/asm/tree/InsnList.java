package org.objectweb.asm.tree;

import java.util.ListIterator;
import org.objectweb.asm.MethodVisitor;

public class InsnList {
   private int size;
   private AbstractInsnNode firstInsn;
   private AbstractInsnNode lastInsn;
   AbstractInsnNode[] cache;

   public InsnList() {
      super();
   }

   public int size() {
      return this.size;
   }

   public AbstractInsnNode getFirst() {
      return this.firstInsn;
   }

   public AbstractInsnNode getLast() {
      return this.lastInsn;
   }

   public AbstractInsnNode get(int var1) {
      if (var1 >= 0 && var1 < this.size) {
         if (this.cache == null) {
            this.cache = this.toArray();
         }

         return this.cache[var1];
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public boolean contains(AbstractInsnNode var1) {
      AbstractInsnNode var2;
      for(var2 = this.firstInsn; var2 != null && var2 != var1; var2 = var2.nextInsn) {
      }

      return var2 != null;
   }

   public int indexOf(AbstractInsnNode var1) {
      if (this.cache == null) {
         this.cache = this.toArray();
      }

      return var1.index;
   }

   public void accept(MethodVisitor var1) {
      for(AbstractInsnNode var2 = this.firstInsn; var2 != null; var2 = var2.nextInsn) {
         var2.accept(var1);
      }

   }

   public ListIterator iterator() {
      return this.iterator(0);
   }

   public ListIterator iterator(int var1) {
      return new InsnList$InsnListIterator(this, var1);
   }

   public AbstractInsnNode[] toArray() {
      int var1 = 0;
      AbstractInsnNode var2 = this.firstInsn;

      AbstractInsnNode[] var3;
      for(var3 = new AbstractInsnNode[this.size]; var2 != null; var2 = var2.nextInsn) {
         var3[var1] = var2;
         var2.index = var1++;
      }

      return var3;
   }

   public void set(AbstractInsnNode var1, AbstractInsnNode var2) {
      AbstractInsnNode var3 = var1.nextInsn;
      var2.nextInsn = var3;
      if (var3 != null) {
         var3.previousInsn = var2;
      } else {
         this.lastInsn = var2;
      }

      AbstractInsnNode var4 = var1.previousInsn;
      var2.previousInsn = var4;
      if (var4 != null) {
         var4.nextInsn = var2;
      } else {
         this.firstInsn = var2;
      }

      if (this.cache != null) {
         int var5 = var1.index;
         this.cache[var5] = var2;
         var2.index = var5;
      } else {
         var2.index = 0;
      }

      var1.index = -1;
      var1.previousInsn = null;
      var1.nextInsn = null;
   }

   public void add(AbstractInsnNode var1) {
      ++this.size;
      if (this.lastInsn == null) {
         this.firstInsn = var1;
         this.lastInsn = var1;
      } else {
         this.lastInsn.nextInsn = var1;
         var1.previousInsn = this.lastInsn;
      }

      this.lastInsn = var1;
      this.cache = null;
      var1.index = 0;
   }

   public void add(InsnList var1) {
      if (var1.size != 0) {
         this.size += var1.size;
         if (this.lastInsn == null) {
            this.firstInsn = var1.firstInsn;
            this.lastInsn = var1.lastInsn;
         } else {
            AbstractInsnNode var2 = var1.firstInsn;
            this.lastInsn.nextInsn = var2;
            var2.previousInsn = this.lastInsn;
            this.lastInsn = var1.lastInsn;
         }

         this.cache = null;
         var1.removeAll(false);
      }
   }

   public void insert(AbstractInsnNode var1) {
      ++this.size;
      if (this.firstInsn == null) {
         this.firstInsn = var1;
         this.lastInsn = var1;
      } else {
         this.firstInsn.previousInsn = var1;
         var1.nextInsn = this.firstInsn;
      }

      this.firstInsn = var1;
      this.cache = null;
      var1.index = 0;
   }

   public void insert(InsnList var1) {
      if (var1.size != 0) {
         this.size += var1.size;
         if (this.firstInsn == null) {
            this.firstInsn = var1.firstInsn;
            this.lastInsn = var1.lastInsn;
         } else {
            AbstractInsnNode var2 = var1.lastInsn;
            this.firstInsn.previousInsn = var2;
            var2.nextInsn = this.firstInsn;
            this.firstInsn = var1.firstInsn;
         }

         this.cache = null;
         var1.removeAll(false);
      }
   }

   public void insert(AbstractInsnNode var1, AbstractInsnNode var2) {
      ++this.size;
      AbstractInsnNode var3 = var1.nextInsn;
      if (var3 == null) {
         this.lastInsn = var2;
      } else {
         var3.previousInsn = var2;
      }

      var1.nextInsn = var2;
      var2.nextInsn = var3;
      var2.previousInsn = var1;
      this.cache = null;
      var2.index = 0;
   }

   public void insert(AbstractInsnNode var1, InsnList var2) {
      if (var2.size != 0) {
         this.size += var2.size;
         AbstractInsnNode var3 = var2.firstInsn;
         AbstractInsnNode var4 = var2.lastInsn;
         AbstractInsnNode var5 = var1.nextInsn;
         if (var5 == null) {
            this.lastInsn = var4;
         } else {
            var5.previousInsn = var4;
         }

         var1.nextInsn = var3;
         var4.nextInsn = var5;
         var3.previousInsn = var1;
         this.cache = null;
         var2.removeAll(false);
      }
   }

   public void insertBefore(AbstractInsnNode var1, AbstractInsnNode var2) {
      ++this.size;
      AbstractInsnNode var3 = var1.previousInsn;
      if (var3 == null) {
         this.firstInsn = var2;
      } else {
         var3.nextInsn = var2;
      }

      var1.previousInsn = var2;
      var2.nextInsn = var1;
      var2.previousInsn = var3;
      this.cache = null;
      var2.index = 0;
   }

   public void insertBefore(AbstractInsnNode var1, InsnList var2) {
      if (var2.size != 0) {
         this.size += var2.size;
         AbstractInsnNode var3 = var2.firstInsn;
         AbstractInsnNode var4 = var2.lastInsn;
         AbstractInsnNode var5 = var1.previousInsn;
         if (var5 == null) {
            this.firstInsn = var3;
         } else {
            var5.nextInsn = var3;
         }

         var1.previousInsn = var4;
         var4.nextInsn = var1;
         var3.previousInsn = var5;
         this.cache = null;
         var2.removeAll(false);
      }
   }

   public void remove(AbstractInsnNode var1) {
      --this.size;
      AbstractInsnNode var2 = var1.nextInsn;
      AbstractInsnNode var3 = var1.previousInsn;
      if (var2 == null) {
         if (var3 == null) {
            this.firstInsn = null;
            this.lastInsn = null;
         } else {
            var3.nextInsn = null;
            this.lastInsn = var3;
         }
      } else if (var3 == null) {
         this.firstInsn = var2;
         var2.previousInsn = null;
      } else {
         var3.nextInsn = var2;
         var2.previousInsn = var3;
      }

      this.cache = null;
      var1.index = -1;
      var1.previousInsn = null;
      var1.nextInsn = null;
   }

   void removeAll(boolean var1) {
      AbstractInsnNode var3;
      if (var1) {
         for(AbstractInsnNode var2 = this.firstInsn; var2 != null; var2 = var3) {
            var3 = var2.nextInsn;
            var2.index = -1;
            var2.previousInsn = null;
            var2.nextInsn = null;
         }
      }

      this.size = 0;
      this.firstInsn = null;
      this.lastInsn = null;
      this.cache = null;
   }

   public void clear() {
      this.removeAll(false);
   }

   public void resetLabels() {
      for(AbstractInsnNode var1 = this.firstInsn; var1 != null; var1 = var1.nextInsn) {
         if (var1 instanceof LabelNode) {
            ((LabelNode)var1).resetLabel();
         }
      }

   }
}
