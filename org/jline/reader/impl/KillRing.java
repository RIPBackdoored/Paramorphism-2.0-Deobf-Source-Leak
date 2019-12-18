package org.jline.reader.impl;

public final class KillRing {
   private static final int DEFAULT_SIZE = 60;
   private final String[] slots;
   private int head;
   private boolean lastKill;
   private boolean lastYank;

   public KillRing(int var1) {
      super();
      this.head = 0;
      this.lastKill = false;
      this.lastYank = false;
      this.slots = new String[var1];
   }

   public KillRing() {
      this(60);
   }

   public void resetLastYank() {
      this.lastYank = false;
   }

   public void resetLastKill() {
      this.lastKill = false;
   }

   public boolean lastYank() {
      return this.lastYank;
   }

   public void add(String var1) {
      this.lastYank = false;
      if (this.lastKill && this.slots[this.head] != null) {
         StringBuilder var10000 = new StringBuilder();
         String[] var10002 = this.slots;
         int var10004 = this.head;
         var10002[var10004] = var10000.append(var10002[var10004]).append(var1).toString();
      } else {
         this.lastKill = true;
         this.next();
         this.slots[this.head] = var1;
      }
   }

   public void addBackwards(String var1) {
      this.lastYank = false;
      if (this.lastKill && this.slots[this.head] != null) {
         this.slots[this.head] = var1 + this.slots[this.head];
      } else {
         this.lastKill = true;
         this.next();
         this.slots[this.head] = var1;
      }
   }

   public String yank() {
      this.lastKill = false;
      this.lastYank = true;
      return this.slots[this.head];
   }

   public String yankPop() {
      this.lastKill = false;
      if (this.lastYank) {
         this.prev();
         return this.slots[this.head];
      } else {
         return null;
      }
   }

   private void next() {
      if (this.head != 0 || this.slots[0] != null) {
         ++this.head;
         if (this.head == this.slots.length) {
            this.head = 0;
         }

      }
   }

   private void prev() {
      --this.head;
      if (this.head == -1) {
         int var1;
         for(var1 = this.slots.length - 1; var1 >= 0 && this.slots[var1] == null; --var1) {
         }

         this.head = var1;
      }

   }
}
