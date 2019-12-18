package org.eclipse.aether.util.graph.traverser;

import org.eclipse.aether.collection.DependencyCollectionContext;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.graph.Dependency;

public final class FatArtifactTraverser implements DependencyTraverser {
   public FatArtifactTraverser() {
      super();
   }

   public boolean traverseDependency(Dependency var1) {
      String var2 = var1.getArtifact().getProperty("includesDependencies", "");
      return !Boolean.parseBoolean(var2);
   }

   public DependencyTraverser deriveChildTraverser(DependencyCollectionContext var1) {
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
