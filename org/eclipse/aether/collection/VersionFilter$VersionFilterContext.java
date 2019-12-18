package org.eclipse.aether.collection;

import java.util.Iterator;
import java.util.List;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.ArtifactRepository;
import org.eclipse.aether.version.Version;
import org.eclipse.aether.version.VersionConstraint;

public interface VersionFilter$VersionFilterContext extends Iterable {
   RepositorySystemSession getSession();

   Dependency getDependency();

   int getCount();

   Iterator iterator();

   VersionConstraint getVersionConstraint();

   ArtifactRepository getRepository(Version var1);

   List getRepositories();
}
