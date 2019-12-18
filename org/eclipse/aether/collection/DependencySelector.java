package org.eclipse.aether.collection;

import org.eclipse.aether.graph.Dependency;

public interface DependencySelector {
   boolean selectDependency(Dependency var1);

   DependencySelector deriveChildSelector(DependencyCollectionContext var1);
}
