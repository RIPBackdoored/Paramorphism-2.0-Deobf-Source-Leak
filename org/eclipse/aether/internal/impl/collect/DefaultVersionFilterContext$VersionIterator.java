package org.eclipse.aether.internal.impl.collect;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.eclipse.aether.version.Version;

class DefaultVersionFilterContext$VersionIterator implements Iterator {
   private final List versions;
   private final int size;
   private int count;
   private int index;
   private int next;
   final DefaultVersionFilterContext this$0;

   DefaultVersionFilterContext$VersionIterator(DefaultVersionFilterContext var1) {
      super();
      this.this$0 = var1;
      this.count = var1.count;
      this.index = -1;
      this.next = 0;
      this.versions = var1.result.getVersions();
      this.size = this.versions.size();
      this.advance();
   }

   private void advance() {
      for(this.next = this.index + 1; this.next < this.size && this.this$0.deleted[this.next] != 0; ++this.next) {
      }

   }

   public boolean hasNext() {
      return this.next < this.size;
   }

   public Version next() {
      if (this.count != this.this$0.count) {
         throw new ConcurrentModificationException();
      } else if (this.next >= this.size) {
         throw new NoSuchElementException();
      } else {
         this.index = this.next;
         this.advance();
         return (Version)this.versions.get(this.index);
      }
   }

   public void remove() {
      if (this.count != this.this$0.count) {
         throw new ConcurrentModificationException();
      } else if (this.index >= 0 && this.this$0.deleted[this.index] != 1) {
         this.this$0.deleted[this.index] = 1;
         this.count = --this.this$0.count;
      } else {
         throw new IllegalStateException();
      }
   }

   public String toString() {
      return this.index < 0 ? "null" : String.valueOf(this.versions.get(this.index));
   }

   public Object next() {
      return this.next();
   }
}
