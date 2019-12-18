package org.eclipse.aether.collection;

import java.util.List;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.Dependency;

public interface DependencyCollectionContext {
   RepositorySystemSession getSession();

   Artifact getArtifact();

   Dependency getDependency();

   List getManagedDependencies();
}
