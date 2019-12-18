package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.collection.DependencyGraphTransformationContext;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.graph.DependencyNode;

public final class NoopDependencyGraphTransformer implements DependencyGraphTransformer {
   public static final DependencyGraphTransformer INSTANCE = new NoopDependencyGraphTransformer();

   public NoopDependencyGraphTransformer() {
      super();
   }

   public DependencyNode transformGraph(DependencyNode var1, DependencyGraphTransformationContext var2) throws RepositoryException {
      return var1;
   }
}
