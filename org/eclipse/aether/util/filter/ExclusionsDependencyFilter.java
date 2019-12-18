package org.eclipse.aether.util.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;

public final class ExclusionsDependencyFilter implements DependencyFilter {
   private final Set excludes = new HashSet();

   public ExclusionsDependencyFilter(Collection var1) {
      super();
      if (var1 != null) {
         this.excludes.addAll(var1);
      }

   }

   public boolean accept(DependencyNode var1, List var2) {
      Dependency var3 = var1.getDependency();
      if (var3 == null) {
         return true;
      } else {
         String var4 = var3.getArtifact().getArtifactId();
         if (this.excludes.contains(var4)) {
            return false;
         } else {
            var4 = var3.getArtifact().getGroupId() + ':' + var4;
            return !this.excludes.contains(var4);
         }
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass().equals(var1.getClass())) {
         ExclusionsDependencyFilter var2 = (ExclusionsDependencyFilter)var1;
         return this.excludes.equals(var2.excludes);
      } else {
         return false;
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.excludes.hashCode();
      return var2;
   }
}
