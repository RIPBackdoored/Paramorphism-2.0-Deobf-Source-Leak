package com.fasterxml.jackson.databind.util;

public final class LinkedNode {
   private final Object value;
   private LinkedNode next;

   public LinkedNode(Object var1, LinkedNode var2) {
      super();
      this.value = var1;
      this.next = var2;
   }

   public void linkNext(LinkedNode var1) {
      if (this.next != null) {
         throw new IllegalStateException();
      } else {
         this.next = var1;
      }
   }

   public LinkedNode next() {
      return this.next;
   }

   public Object value() {
      return this.value;
   }

   public static boolean contains(LinkedNode var0, Object var1) {
      while(var0 != null) {
         if (var0.value() == var1) {
            return true;
         }

         var0 = var0.next();
      }

      return false;
   }
}
