package org.eclipse.aether.util.graph.transformer;

final class ConflictIdSorter$RootQueue {
   private int nextOut;
   private int nextIn;
   private ConflictIdSorter$ConflictId[] ids;

   ConflictIdSorter$RootQueue(int var1) {
      super();
      this.ids = new ConflictIdSorter$ConflictId[var1 + 16];
   }

   boolean isEmpty() {
      return this.nextOut >= this.nextIn;
   }

   void add(ConflictIdSorter$ConflictId var1) {
      if (this.nextOut >= this.nextIn && this.nextOut > 0) {
         this.nextIn -= this.nextOut;
         this.nextOut = 0;
      }

      if (this.nextIn >= this.ids.length) {
         ConflictIdSorter$ConflictId[] var2 = new ConflictIdSorter$ConflictId[this.ids.length + this.ids.length / 2 + 16];
         System.arraycopy(this.ids, this.nextOut, var2, 0, this.nextIn - this.nextOut);
         this.ids = var2;
         this.nextIn -= this.nextOut;
         this.nextOut = 0;
      }

      int var3;
      for(var3 = this.nextIn - 1; var3 >= this.nextOut && var1.minDepth < this.ids[var3].minDepth; --var3) {
         this.ids[var3 + 1] = this.ids[var3];
      }

      this.ids[var3 + 1] = var1;
      ++this.nextIn;
   }

   ConflictIdSorter$ConflictId remove() {
      return this.ids[this.nextOut++];
   }
}
