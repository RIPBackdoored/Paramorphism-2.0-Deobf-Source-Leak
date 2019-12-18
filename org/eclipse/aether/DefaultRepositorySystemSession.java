package org.eclipse.aether;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
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

public final class DefaultRepositorySystemSession implements RepositorySystemSession {
   private boolean readOnly;
   private boolean offline;
   private boolean ignoreArtifactDescriptorRepositories;
   private ResolutionErrorPolicy resolutionErrorPolicy;
   private ArtifactDescriptorPolicy artifactDescriptorPolicy;
   private String checksumPolicy;
   private String updatePolicy;
   private LocalRepositoryManager localRepositoryManager;
   private FileTransformerManager fileTransformerManager;
   private WorkspaceReader workspaceReader;
   private RepositoryListener repositoryListener;
   private TransferListener transferListener;
   private Map systemProperties;
   private Map systemPropertiesView;
   private Map userProperties;
   private Map userPropertiesView;
   private Map configProperties;
   private Map configPropertiesView;
   private MirrorSelector mirrorSelector;
   private ProxySelector proxySelector;
   private AuthenticationSelector authenticationSelector;
   private ArtifactTypeRegistry artifactTypeRegistry;
   private DependencyTraverser dependencyTraverser;
   private DependencyManager dependencyManager;
   private DependencySelector dependencySelector;
   private VersionFilter versionFilter;
   private DependencyGraphTransformer dependencyGraphTransformer;
   private SessionData data;
   private RepositoryCache cache;

   public DefaultRepositorySystemSession() {
      super();
      this.systemProperties = new HashMap();
      this.systemPropertiesView = Collections.unmodifiableMap(this.systemProperties);
      this.userProperties = new HashMap();
      this.userPropertiesView = Collections.unmodifiableMap(this.userProperties);
      this.configProperties = new HashMap();
      this.configPropertiesView = Collections.unmodifiableMap(this.configProperties);
      this.mirrorSelector = DefaultRepositorySystemSession$NullMirrorSelector.INSTANCE;
      this.proxySelector = DefaultRepositorySystemSession$NullProxySelector.INSTANCE;
      this.authenticationSelector = DefaultRepositorySystemSession$NullAuthenticationSelector.INSTANCE;
      this.artifactTypeRegistry = DefaultRepositorySystemSession$NullArtifactTypeRegistry.INSTANCE;
      this.fileTransformerManager = DefaultRepositorySystemSession$NullFileTransformerManager.INSTANCE;
      this.data = new DefaultSessionData();
   }

   public DefaultRepositorySystemSession(RepositorySystemSession var1) {
      super();
      Objects.requireNonNull(var1, "repository system session cannot be null");
      this.setOffline(var1.isOffline());
      this.setIgnoreArtifactDescriptorRepositories(var1.isIgnoreArtifactDescriptorRepositories());
      this.setResolutionErrorPolicy(var1.getResolutionErrorPolicy());
      this.setArtifactDescriptorPolicy(var1.getArtifactDescriptorPolicy());
      this.setChecksumPolicy(var1.getChecksumPolicy());
      this.setUpdatePolicy(var1.getUpdatePolicy());
      this.setLocalRepositoryManager(var1.getLocalRepositoryManager());
      this.setWorkspaceReader(var1.getWorkspaceReader());
      this.setRepositoryListener(var1.getRepositoryListener());
      this.setTransferListener(var1.getTransferListener());
      this.setSystemProperties(var1.getSystemProperties());
      this.setUserProperties(var1.getUserProperties());
      this.setConfigProperties(var1.getConfigProperties());
      this.setMirrorSelector(var1.getMirrorSelector());
      this.setProxySelector(var1.getProxySelector());
      this.setAuthenticationSelector(var1.getAuthenticationSelector());
      this.setArtifactTypeRegistry(var1.getArtifactTypeRegistry());
      this.setDependencyTraverser(var1.getDependencyTraverser());
      this.setDependencyManager(var1.getDependencyManager());
      this.setDependencySelector(var1.getDependencySelector());
      this.setVersionFilter(var1.getVersionFilter());
      this.setDependencyGraphTransformer(var1.getDependencyGraphTransformer());
      this.setFileTransformerManager(var1.getFileTransformerManager());
      this.setData(var1.getData());
      this.setCache(var1.getCache());
   }

   public boolean isOffline() {
      return this.offline;
   }

   public DefaultRepositorySystemSession setOffline(boolean var1) {
      this.failIfReadOnly();
      this.offline = var1;
      return this;
   }

   public boolean isIgnoreArtifactDescriptorRepositories() {
      return this.ignoreArtifactDescriptorRepositories;
   }

   public DefaultRepositorySystemSession setIgnoreArtifactDescriptorRepositories(boolean var1) {
      this.failIfReadOnly();
      this.ignoreArtifactDescriptorRepositories = var1;
      return this;
   }

   public ResolutionErrorPolicy getResolutionErrorPolicy() {
      return this.resolutionErrorPolicy;
   }

   public DefaultRepositorySystemSession setResolutionErrorPolicy(ResolutionErrorPolicy var1) {
      this.failIfReadOnly();
      this.resolutionErrorPolicy = var1;
      return this;
   }

   public ArtifactDescriptorPolicy getArtifactDescriptorPolicy() {
      return this.artifactDescriptorPolicy;
   }

   public DefaultRepositorySystemSession setArtifactDescriptorPolicy(ArtifactDescriptorPolicy var1) {
      this.failIfReadOnly();
      this.artifactDescriptorPolicy = var1;
      return this;
   }

   public String getChecksumPolicy() {
      return this.checksumPolicy;
   }

   public DefaultRepositorySystemSession setChecksumPolicy(String var1) {
      this.failIfReadOnly();
      this.checksumPolicy = var1;
      return this;
   }

   public String getUpdatePolicy() {
      return this.updatePolicy;
   }

   public DefaultRepositorySystemSession setUpdatePolicy(String var1) {
      this.failIfReadOnly();
      this.updatePolicy = var1;
      return this;
   }

   public LocalRepository getLocalRepository() {
      LocalRepositoryManager var1 = this.getLocalRepositoryManager();
      return var1 != null ? var1.getRepository() : null;
   }

   public LocalRepositoryManager getLocalRepositoryManager() {
      return this.localRepositoryManager;
   }

   public DefaultRepositorySystemSession setLocalRepositoryManager(LocalRepositoryManager var1) {
      this.failIfReadOnly();
      this.localRepositoryManager = var1;
      return this;
   }

   public FileTransformerManager getFileTransformerManager() {
      return this.fileTransformerManager;
   }

   public DefaultRepositorySystemSession setFileTransformerManager(FileTransformerManager var1) {
      this.failIfReadOnly();
      this.fileTransformerManager = var1;
      if (this.fileTransformerManager == null) {
         this.fileTransformerManager = DefaultRepositorySystemSession$NullFileTransformerManager.INSTANCE;
      }

      return this;
   }

   public WorkspaceReader getWorkspaceReader() {
      return this.workspaceReader;
   }

   public DefaultRepositorySystemSession setWorkspaceReader(WorkspaceReader var1) {
      this.failIfReadOnly();
      this.workspaceReader = var1;
      return this;
   }

   public RepositoryListener getRepositoryListener() {
      return this.repositoryListener;
   }

   public DefaultRepositorySystemSession setRepositoryListener(RepositoryListener var1) {
      this.failIfReadOnly();
      this.repositoryListener = var1;
      return this;
   }

   public TransferListener getTransferListener() {
      return this.transferListener;
   }

   public DefaultRepositorySystemSession setTransferListener(TransferListener var1) {
      this.failIfReadOnly();
      this.transferListener = var1;
      return this;
   }

   private Map copySafe(Map var1, Class var2) {
      HashMap var3;
      if (var1 != null && !var1.isEmpty()) {
         var3 = new HashMap((int)((float)var1.size() / 0.75F) + 1);
         Iterator var4 = var1.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            Object var6 = var5.getKey();
            if (var6 instanceof String) {
               Object var7 = var5.getValue();
               if (var2.isInstance(var7)) {
                  var3.put(var6.toString(), var2.cast(var7));
               }
            }
         }
      } else {
         var3 = new HashMap();
      }

      return var3;
   }

   public Map getSystemProperties() {
      return this.systemPropertiesView;
   }

   public DefaultRepositorySystemSession setSystemProperties(Map var1) {
      this.failIfReadOnly();
      this.systemProperties = this.copySafe(var1, String.class);
      this.systemPropertiesView = Collections.unmodifiableMap(this.systemProperties);
      return this;
   }

   public DefaultRepositorySystemSession setSystemProperty(String var1, String var2) {
      this.failIfReadOnly();
      if (var2 != null) {
         this.systemProperties.put(var1, var2);
      } else {
         this.systemProperties.remove(var1);
      }

      return this;
   }

   public Map getUserProperties() {
      return this.userPropertiesView;
   }

   public DefaultRepositorySystemSession setUserProperties(Map var1) {
      this.failIfReadOnly();
      this.userProperties = this.copySafe(var1, String.class);
      this.userPropertiesView = Collections.unmodifiableMap(this.userProperties);
      return this;
   }

   public DefaultRepositorySystemSession setUserProperty(String var1, String var2) {
      this.failIfReadOnly();
      if (var2 != null) {
         this.userProperties.put(var1, var2);
      } else {
         this.userProperties.remove(var1);
      }

      return this;
   }

   public Map getConfigProperties() {
      return this.configPropertiesView;
   }

   public DefaultRepositorySystemSession setConfigProperties(Map var1) {
      this.failIfReadOnly();
      this.configProperties = this.copySafe(var1, Object.class);
      this.configPropertiesView = Collections.unmodifiableMap(this.configProperties);
      return this;
   }

   public DefaultRepositorySystemSession setConfigProperty(String var1, Object var2) {
      this.failIfReadOnly();
      if (var2 != null) {
         this.configProperties.put(var1, var2);
      } else {
         this.configProperties.remove(var1);
      }

      return this;
   }

   public MirrorSelector getMirrorSelector() {
      return this.mirrorSelector;
   }

   public DefaultRepositorySystemSession setMirrorSelector(MirrorSelector var1) {
      this.failIfReadOnly();
      this.mirrorSelector = var1;
      if (this.mirrorSelector == null) {
         this.mirrorSelector = DefaultRepositorySystemSession$NullMirrorSelector.INSTANCE;
      }

      return this;
   }

   public ProxySelector getProxySelector() {
      return this.proxySelector;
   }

   public DefaultRepositorySystemSession setProxySelector(ProxySelector var1) {
      this.failIfReadOnly();
      this.proxySelector = var1;
      if (this.proxySelector == null) {
         this.proxySelector = DefaultRepositorySystemSession$NullProxySelector.INSTANCE;
      }

      return this;
   }

   public AuthenticationSelector getAuthenticationSelector() {
      return this.authenticationSelector;
   }

   public DefaultRepositorySystemSession setAuthenticationSelector(AuthenticationSelector var1) {
      this.failIfReadOnly();
      this.authenticationSelector = var1;
      if (this.authenticationSelector == null) {
         this.authenticationSelector = DefaultRepositorySystemSession$NullAuthenticationSelector.INSTANCE;
      }

      return this;
   }

   public ArtifactTypeRegistry getArtifactTypeRegistry() {
      return this.artifactTypeRegistry;
   }

   public DefaultRepositorySystemSession setArtifactTypeRegistry(ArtifactTypeRegistry var1) {
      this.failIfReadOnly();
      this.artifactTypeRegistry = var1;
      if (this.artifactTypeRegistry == null) {
         this.artifactTypeRegistry = DefaultRepositorySystemSession$NullArtifactTypeRegistry.INSTANCE;
      }

      return this;
   }

   public DependencyTraverser getDependencyTraverser() {
      return this.dependencyTraverser;
   }

   public DefaultRepositorySystemSession setDependencyTraverser(DependencyTraverser var1) {
      this.failIfReadOnly();
      this.dependencyTraverser = var1;
      return this;
   }

   public DependencyManager getDependencyManager() {
      return this.dependencyManager;
   }

   public DefaultRepositorySystemSession setDependencyManager(DependencyManager var1) {
      this.failIfReadOnly();
      this.dependencyManager = var1;
      return this;
   }

   public DependencySelector getDependencySelector() {
      return this.dependencySelector;
   }

   public DefaultRepositorySystemSession setDependencySelector(DependencySelector var1) {
      this.failIfReadOnly();
      this.dependencySelector = var1;
      return this;
   }

   public VersionFilter getVersionFilter() {
      return this.versionFilter;
   }

   public DefaultRepositorySystemSession setVersionFilter(VersionFilter var1) {
      this.failIfReadOnly();
      this.versionFilter = var1;
      return this;
   }

   public DependencyGraphTransformer getDependencyGraphTransformer() {
      return this.dependencyGraphTransformer;
   }

   public DefaultRepositorySystemSession setDependencyGraphTransformer(DependencyGraphTransformer var1) {
      this.failIfReadOnly();
      this.dependencyGraphTransformer = var1;
      return this;
   }

   public SessionData getData() {
      return this.data;
   }

   public DefaultRepositorySystemSession setData(SessionData var1) {
      this.failIfReadOnly();
      this.data = var1;
      if (this.data == null) {
         this.data = new DefaultSessionData();
      }

      return this;
   }

   public RepositoryCache getCache() {
      return this.cache;
   }

   public DefaultRepositorySystemSession setCache(RepositoryCache var1) {
      this.failIfReadOnly();
      this.cache = var1;
      return this;
   }

   public void setReadOnly() {
      this.readOnly = true;
   }

   private void failIfReadOnly() {
      if (this.readOnly) {
         throw new IllegalStateException("repository system session is read-only");
      }
   }
}
