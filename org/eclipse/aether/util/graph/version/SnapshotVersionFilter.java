package org.eclipse.aether.util.graph.version;

import java.util.Iterator;
import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.VersionFilter;
import org.eclipse.aether.collection.VersionFilter$VersionFilterContext;
import org.eclipse.aether.version.Version;

public final class SnapshotVersionFilter implements VersionFilter {
   public SnapshotVersionFilter() {
      super();
   }

   public void filterVersions(VersionFilter$VersionFilterContext var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         String var3 = ((Version)var2.next()).toString();
         if (var3.endsWith("SNAPSHOT")) {
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
