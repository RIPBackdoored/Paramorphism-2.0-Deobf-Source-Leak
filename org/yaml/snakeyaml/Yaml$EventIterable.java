package org.yaml.snakeyaml;

import java.util.Iterator;

class Yaml$EventIterable implements Iterable {
   private Iterator iterator;

   public Yaml$EventIterable(Iterator var1) {
      super();
      this.iterator = var1;
   }

   public Iterator iterator() {
      return this.iterator;
   }
}
