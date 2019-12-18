package org.eclipse.aether.impl;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;

public interface DependencyCollector {
   CollectResult collectDependencies(RepositorySystemSession var1, CollectRequest var2) throws DependencyCollectionException;
}
