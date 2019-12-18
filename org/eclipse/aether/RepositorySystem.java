package org.eclipse.aether;

import java.util.Collection;
import java.util.List;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.deployment.DeployRequest;
import org.eclipse.aether.deployment.DeployResult;
import org.eclipse.aether.deployment.DeploymentException;
import org.eclipse.aether.installation.InstallRequest;
import org.eclipse.aether.installation.InstallResult;
import org.eclipse.aether.installation.InstallationException;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.resolution.DependencyResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.resolution.VersionRequest;
import org.eclipse.aether.resolution.VersionResolutionException;
import org.eclipse.aether.resolution.VersionResult;

public interface RepositorySystem {
   VersionRangeResult resolveVersionRange(RepositorySystemSession var1, VersionRangeRequest var2) throws VersionRangeResolutionException;

   VersionResult resolveVersion(RepositorySystemSession var1, VersionRequest var2) throws VersionResolutionException;

   ArtifactDescriptorResult readArtifactDescriptor(RepositorySystemSession var1, ArtifactDescriptorRequest var2) throws ArtifactDescriptorException;

   CollectResult collectDependencies(RepositorySystemSession var1, CollectRequest var2) throws DependencyCollectionException;

   DependencyResult resolveDependencies(RepositorySystemSession var1, DependencyRequest var2) throws DependencyResolutionException;

   ArtifactResult resolveArtifact(RepositorySystemSession var1, ArtifactRequest var2) throws ArtifactResolutionException;

   List resolveArtifacts(RepositorySystemSession var1, Collection var2) throws ArtifactResolutionException;

   List resolveMetadata(RepositorySystemSession var1, Collection var2);

   InstallResult install(RepositorySystemSession var1, InstallRequest var2) throws InstallationException;

   DeployResult deploy(RepositorySystemSession var1, DeployRequest var2) throws DeploymentException;

   LocalRepositoryManager newLocalRepositoryManager(RepositorySystemSession var1, LocalRepository var2);

   SyncContext newSyncContext(RepositorySystemSession var1, boolean var2);

   List newResolutionRepositories(RepositorySystemSession var1, List var2);

   RemoteRepository newDeploymentRepository(RepositorySystemSession var1, RemoteRepository var2);
}
