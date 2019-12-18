package org.eclipse.aether.collection;

import org.eclipse.aether.RepositoryException;

public interface VersionFilter {
   void filterVersions(VersionFilter$VersionFilterContext var1) throws RepositoryException;

   VersionFilter deriveChildFilter(DependencyCollectionContext var1);
}
