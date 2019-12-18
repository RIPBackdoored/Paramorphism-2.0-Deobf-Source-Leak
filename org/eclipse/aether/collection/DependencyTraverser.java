package org.eclipse.aether.collection;

import org.eclipse.aether.graph.Dependency;

public interface DependencyTraverser {
   boolean traverseDependency(Dependency var1);

   DependencyTraverser deriveChildTraverser(DependencyCollectionContext var1);
}
