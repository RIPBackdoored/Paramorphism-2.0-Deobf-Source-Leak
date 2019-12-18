package org.eclipse.aether.util.graph.version;

import java.util.Iterator;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.VersionFilter;
import org.eclipse.aether.collection.VersionFilter$VersionFilterContext;

public final class HighestVersionFilter implements VersionFilter {
   public HighestVersionFilter() {
      super();
   }

   public void filterVersions(VersionFilter$VersionFilterContext var1) {
      Iterator var2 = var1.iterator();
      boolean var3 = var2.hasNext();

      while(var3) {
         var2.next();
         var3 = var2.hasNext();
         if (var3) {
            var2.remove();
         }
      }

   }

   public VersionFilter deriveChildFilter(DependencyCollectionContext var1) {
      return this;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return null != var1 && this.getClass().equals(var1.getClass());
      }
   }

   public int hashCode() {
      return this.getClass().hashCode();
   }
}
