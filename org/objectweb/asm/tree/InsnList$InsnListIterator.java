package org.objectweb.asm.tree;

import java.util.ListIterator;
import java.util.NoSuchElementException;

final class InsnList$InsnListIterator implements ListIterator {
   AbstractInsnNode nextInsn;
   AbstractInsnNode previousInsn;
   AbstractInsnNode remove;
   final InsnList this$0;

   InsnList$InsnListIterator(InsnList var1, int var2) {
      super();
      this.this$0 = var1;
      if (var2 == var1.size()) {
         this.nextInsn = null;
         this.previousInsn = var1.getLast();
      } else {
         this.nextInsn = var1.get(var2);
         this.previousInsn = this.nextInsn.previousInsn;
      }

   }

   public boolean hasNext() {
      return this.nextInsn != null;
   }

   public Object next() {
      if (this.nextInsn == null) {
         throw new NoSuchElementException();
      } else {
         AbstractInsnNode var1 = this.nextInsn;
         this.previousInsn = var1;
         this.nextInsn = var1.nextInsn;
         this.remove = var1;
         return var1;
      }
   }

   public void remove() {
      if (this.remove != null) {
         if (this.remove == this.nextInsn) {
            this.nextInsn = this.nextInsn.nextInsn;
         } else {
            this.previousInsn = this.previousInsn.previousInsn;
         }

         this.this$0.remove(this.remove);
         this.remove = null;
      } else {
         throw new IllegalStateException();
      }
   }

   public boolean hasPrevious() {
      return this.previousInsn != null;
   }

   public Object previous() {
      if (this.previousInsn == null) {
         throw new NoSuchElementException();
      } else {
         AbstractInsnNode var1 = this.previousInsn;
         this.nextInsn = var1;
         this.previousInsn = var1.previousInsn;
         this.remove = var1;
         return var1;
      }
   }

   public int nextIndex() {
      if (this.nextInsn == null) {
         return this.this$0.size();
      } else {
         if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
         }

         return this.nextInsn.index;
      }
   }

   public int previousIndex() {
      if (this.previousInsn == null) {
         return -1;
      } else {
         if (this.this$0.cache == null) {
            this.this$0.cache = this.this$0.toArray();
         }

         return this.previousInsn.index;
      }
   }

   public void add(Object var1) {
      if (this.nextInsn != null) {
         this.this$0.insertBefore(this.nextInsn, (AbstractInsnNode)var1);
      } else if (this.previousInsn != null) {
         this.this$0.insert(this.previousInsn, (AbstractInsnNode)var1);
      } else {
         this.this$0.add((AbstractInsnNode)var1);
      }

      this.previousInsn = (AbstractInsnNode)var1;
      this.remove = null;
   }

   public void set(Object var1) {
      if (this.remove != null) {
         this.this$0.set(this.remove, (AbstractInsnNode)var1);
         if (this.remove == this.previousInsn) {
            this.previousInsn = (AbstractInsnNode)var1;
         } else {
            this.nextInsn = (AbstractInsnNode)var1;
         }

      } else {
         throw new IllegalStateException();
      }
   }
}
