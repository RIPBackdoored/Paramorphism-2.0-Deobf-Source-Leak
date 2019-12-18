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

public abstract class AbstractForwardingRepositorySystemSession implements RepositorySystemSession {
   protected AbstractForwardingRepositorySystemSession() {
      super();
   }

   protected abstract RepositorySystemSession getSession();

   public boolean isOffline() {
      return this.getSession().isOffline();
   }

   public boolean isIgnoreArtifactDescriptorRepositories() {
      return this.getSession().isIgnoreArtifactDescriptorRepositories();
   }

   public ResolutionErrorPolicy getResolutionErrorPolicy() {
      return this.getSession().getResolutionErrorPolicy();
   }

   public ArtifactDescriptorPolicy getArtifactDescriptorPolicy() {
      return this.getSession().getArtifactDescriptorPolicy();
   }

   public String getChecksumPolicy() {
      return this.getSession().getChecksumPolicy();
   }

   public String getUpdatePolicy() {
      return this.getSession().getUpdatePolicy();
   }

   public LocalRepository getLocalRepository() {
      return this.getSession().getLocalRepository();
   }

   public LocalRepositoryManager getLocalRepositoryManager() {
      return this.getSession().getLocalRepositoryManager();
   }

   public WorkspaceReader getWorkspaceReader() {
      return this.getSession().getWorkspaceReader();
   }

   public RepositoryListener getRepositoryListener() {
      return this.getSession().getRepositoryListener();
   }

   public TransferListener getTransferListener() {
      return this.getSession().getTransferListener();
   }

   public Map getSystemProperties() {
      return this.getSession().getSystemProperties();
   }

   public Map getUserProperties() {
      return this.getSession().getUserProperties();
   }

   public Map getConfigProperties() {
      return this.getSession().getConfigProperties();
   }

   public MirrorSelector getMirrorSelector() {
      return this.getSession().getMirrorSelector();
   }

   public ProxySelector getProxySelector() {
      return this.getSession().getProxySelector();
   }

   public AuthenticationSelector getAuthenticationSelector() {
      return this.getSession().getAuthenticationSelector();
   }

   public ArtifactTypeRegistry getArtifactTypeRegistry() {
      return this.getSession().getArtifactTypeRegistry();
   }

   public DependencyTraverser getDependencyTraverser() {
      return this.getSession().getDependencyTraverser();
   }

   public DependencyManager getDependencyManager() {
      return this.getSession().getDependencyManager();
   }

   public DependencySelector getDependencySelector() {
      return this.getSession().getDependencySelector();
   }

   public VersionFilter getVersionFilter() {
      return this.getSession().getVersionFilter();
   }

   public DependencyGraphTransformer getDependencyGraphTransformer() {
      return this.getSession().getDependencyGraphTransformer();
   }

   public SessionData getData() {
      return this.getSession().getData();
   }

   public RepositoryCache getCache() {
      return this.getSession().getCache();
   }

   public FileTransformerManager getFileTransformerManager() {
      return this.getSession().getFileTransformerManager();
   }
}
