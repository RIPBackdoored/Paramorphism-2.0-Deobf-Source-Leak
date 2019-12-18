package org.eclipse.aether.util.filter;

import java.util.Collection;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.version.VersionScheme;

public final class PatternExclusionsDependencyFilter extends AbstractPatternDependencyFilter {
   public PatternExclusionsDependencyFilter(String... var1) {
      super(var1);
   }

   public PatternExclusionsDependencyFilter(VersionScheme var1, String... var2) {
      super(var1, var2);
   }

   public PatternExclusionsDependencyFilter(Collection var1) {
      super(var1);
   }

   public PatternExclusionsDependencyFilter(VersionScheme var1, Collection var2) {
      super(var1, var2);
   }

   protected boolean accept(Artifact var1) {
      return !super.accept(var1);
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
