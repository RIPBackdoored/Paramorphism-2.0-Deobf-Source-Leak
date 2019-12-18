package org.eclipse.aether.collection;

import org.eclipse.aether.RepositoryException;
import org.eclipse.aether.graph.DependencyNode;

public interface DependencyGraphTransformer {
   DependencyNode transformGraph(DependencyNode var1, DependencyGraphTransformationContext var2) throws RepositoryException;
}
