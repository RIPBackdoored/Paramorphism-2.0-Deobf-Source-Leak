package org.yaml.snakeyaml.representer;

import java.util.Iterator;

class SafeRepresenter$IteratorWrapper implements Iterable {
   private Iterator iter;

   public SafeRepresenter$IteratorWrapper(Iterator var1) {
      super();
      this.iter = var1;
   }

   public Iterator iterator() {
      return this.iter;
   }
}
