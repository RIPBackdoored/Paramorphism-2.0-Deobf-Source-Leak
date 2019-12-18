package com.fasterxml.jackson.databind.introspect;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class POJOPropertyBuilder$MemberIterator implements Iterator {
   private POJOPropertyBuilder$Linked next;

   public POJOPropertyBuilder$MemberIterator(POJOPropertyBuilder$Linked var1) {
      super();
      this.next = var1;
   }

   public boolean hasNext() {
      return this.next != null;
   }

   public AnnotatedMember next() {
      if (this.next == null) {
         throw new NoSuchElementException();
      } else {
         AnnotatedMember var1 = (AnnotatedMember)this.next.value;
         this.next = this.next.next;
         return var1;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public Object next() {
      return this.next();
   }
}
