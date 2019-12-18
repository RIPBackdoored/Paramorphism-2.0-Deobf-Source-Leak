package org.eclipse.aether;

import java.util.Map;
import org.eclipse.aether.artifact.ArtifactTypeRegistry;
import org.eclipse.aether.collection.DependencyGraphTransformer;
import org.eclipse.aether.collection.DependencyManager;
import org.eclipse.aether.collection.DependencySelector;
import org.eclipse.aether.collection.DependencyTraverser;
import org.eclipse.aether.collection.VersionFilter;
import org.eclipse.aether.repository.AuthenticationSelector;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.MirrorSelector;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.WorkspaceReader;
import org.eclipse.aether.resolution.ArtifactDescriptorPolicy;
import org.eclipse.aether.resolution.ResolutionErrorPolicy;
import org.eclipse.aether.transfer.TransferListener;
import org.eclipse.aether.transform.FileTransformerManager;

public interface RepositorySystemSession {
   boolean isOffline();

   boolean isIgnoreArtifactDescriptorRepositories();

   ResolutionErrorPolicy getResolutionErrorPolicy();

   ArtifactDescriptorPolicy getArtifactDescriptorPolicy();

   String getChecksumPolicy();

   String getUpdatePolicy();

   LocalRepository getLocalRepository();

   LocalRepositoryManager getLocalRepositoryManager();

   WorkspaceReader getWorkspaceReader();

   RepositoryListener getRepositoryListener();

   TransferListener getTransferListener();

   Map getSystemProperties();

   Map getUserProperties();

   Map getConfigProperties();

   MirrorSelector getMirrorSelector();

   ProxySelector getProxySelector();

   AuthenticationSelector getAuthenticationSelector();

   ArtifactTypeRegistry getArtifactTypeRegistry();

   DependencyTraverser getDependencyTraverser();

   DependencyManager getDependencyManager();

   DependencySelector getDependencySelector();

   VersionFilter getVersionFilter();

   DependencyGraphTransformer getDependencyGraphTransformer();

   SessionData getData();

   RepositoryCache getCache();

   FileTransformerManager getFileTransformerManager();
}
