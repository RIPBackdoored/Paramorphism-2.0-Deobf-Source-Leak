package org.eclipse.aether.util.graph.transformer;

import java.util.Set;

class ConflictMarker$ConflictGroup {
   final Set keys;
   final int index;

   ConflictMarker$ConflictGroup(Set var1, int var2) {
      super();
      this.keys = var1;
      this.index = var2;
   }

   public String toString() {
      return String.valueOf(this.keys);
   }
}
