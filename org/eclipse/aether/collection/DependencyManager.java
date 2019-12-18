package org.eclipse.aether.collection;

import org.eclipse.aether.graph.Dependency;

public interface DependencyManager {
   DependencyManagement manageDependency(Dependency var1);

   DependencyManager deriveChildManager(DependencyCollectionContext var1);
}
