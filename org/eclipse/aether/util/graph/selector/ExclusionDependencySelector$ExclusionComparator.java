package org.eclipse.aether.util.graph.selector;

import java.util.Comparator;
import org.eclipse.aether.graph.Exclusion;

class ExclusionDependencySelector$ExclusionComparator implements Comparator {
   static final ExclusionDependencySelector$ExclusionComparator INSTANCE = new ExclusionDependencySelector$ExclusionComparator();

   private ExclusionDependencySelector$ExclusionComparator() {
      super();
   }

   public int compare(Exclusion var1, Exclusion var2) {
      if (var1 == null) {
         return var2 == null ? 0 : 1;
      } else if (var2 == null) {
         return -1;
      } else {
         int var3 = var1.getArtifactId().compareTo(var2.getArtifactId());
         if (var3 == 0) {
            var3 = var1.getGroupId().compareTo(var2.getGroupId());
            if (var3 == 0) {
               var3 = var1.getExtension().compareTo(var2.getExtension());
               if (var3 == 0) {
                  var3 = var1.getClassifier().compareTo(var2.getClassifier());
               }
            }
         }

         return var3;
      }
   }

   public int compare(Object var1, Object var2) {
      return this.compare((Exclusion)var1, (Exclusion)var2);
   }
}
