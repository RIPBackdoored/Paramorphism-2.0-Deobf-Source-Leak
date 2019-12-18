package org.eclipse.aether.util.filter;

import java.util.Collection;
import java.util.List;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.version.VersionScheme;

public final class PatternInclusionsDependencyFilter extends AbstractPatternDependencyFilter {
   public PatternInclusionsDependencyFilter(String... var1) {
      super(var1);
   }

   public PatternInclusionsDependencyFilter(VersionScheme var1, String... var2) {
      super(var1, var2);
   }

   public PatternInclusionsDependencyFilter(Collection var1) {
      super(var1);
   }

   public PatternInclusionsDependencyFilter(VersionScheme var1, Collection var2) {
      super(var1, var2);
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   public boolean accept(DependencyNode var1, List var2) {
      return super.accept(var1, var2);
   }
}
