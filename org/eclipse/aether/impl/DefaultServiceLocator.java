package org.eclipse.aether.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.internal.impl.DefaultArtifactResolver;
import org.eclipse.aether.internal.impl.DefaultChecksumPolicyProvider;
import org.eclipse.aether.internal.impl.DefaultDeployer;
import org.eclipse.aether.internal.impl.DefaultFileProcessor;
import org.eclipse.aether.internal.impl.DefaultInstaller;
import org.eclipse.aether.internal.impl.DefaultLocalRepositoryProvider;
import org.eclipse.aether.internal.impl.DefaultMetadataResolver;
import org.eclipse.aether.internal.impl.DefaultOfflineController;
import org.eclipse.aether.internal.impl.DefaultRemoteRepositoryManager;
import org.eclipse.aether.internal.impl.DefaultRepositoryConnectorProvider;
import org.eclipse.aether.internal.impl.DefaultRepositoryEventDispatcher;
import org.eclipse.aether.internal.impl.DefaultRepositoryLayoutProvider;
import org.eclipse.aether.internal.impl.DefaultRepositorySystem;
import org.eclipse.aether.internal.impl.DefaultSyncContextFactory;
import org.eclipse.aether.internal.impl.DefaultTransporterProvider;
import org.eclipse.aether.internal.impl.DefaultUpdateCheckManager;
import org.eclipse.aether.internal.impl.DefaultUpdatePolicyAnalyzer;
import org.eclipse.aether.internal.impl.EnhancedLocalRepositoryManagerFactory;
import org.eclipse.aether.internal.impl.Maven2RepositoryLayoutFactory;
import org.eclipse.aether.internal.impl.SimpleLocalRepositoryManagerFactory;
import org.eclipse.aether.internal.impl.collect.DefaultDependencyCollector;
import org.eclipse.aether.internal.impl.slf4j.Slf4jLoggerFactory;
import org.eclipse.aether.spi.connector.checksum.ChecksumPolicyProvider;
import org.eclipse.aether.spi.connector.layout.RepositoryLayoutFactory;
import org.eclipse.aether.spi.connector.layout.RepositoryLayoutProvider;
import org.eclipse.aether.spi.connector.transport.TransporterProvider;
import org.eclipse.aether.spi.io.FileProcessor;
import org.eclipse.aether.spi.localrepo.LocalRepositoryManagerFactory;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.spi.log.LoggerFactory;

public final class DefaultServiceLocator implements ServiceLocator {
   private final Map entries = new HashMap();
   private DefaultServiceLocator$ErrorHandler errorHandler;

   public DefaultServiceLocator() {
      super();
      this.addService(RepositorySystem.class, DefaultRepositorySystem.class);
      this.addService(ArtifactResolver.class, DefaultArtifactResolver.class);
      this.addService(DependencyCollector.class, DefaultDependencyCollector.class);
      this.addService(Deployer.class, DefaultDeployer.class);
      this.addService(Installer.class, DefaultInstaller.class);
      this.addService(MetadataResolver.class, DefaultMetadataResolver.class);
      this.addService(RepositoryLayoutProvider.class, DefaultRepositoryLayoutProvider.class);
      this.addService(RepositoryLayoutFactory.class, Maven2RepositoryLayoutFactory.class);
      this.addService(TransporterProvider.class, DefaultTransporterProvider.class);
      this.addService(ChecksumPolicyProvider.class, DefaultChecksumPolicyProvider.class);
      this.addService(RepositoryConnectorProvider.class, DefaultRepositoryConnectorProvider.class);
      this.addService(RemoteRepositoryManager.class, DefaultRemoteRepositoryManager.class);
      this.addService(UpdateCheckManager.class, DefaultUpdateCheckManager.class);
      this.addService(UpdatePolicyAnalyzer.class, DefaultUpdatePolicyAnalyzer.class);
      this.addService(FileProcessor.class, DefaultFileProcessor.class);
      this.addService(SyncContextFactory.class, DefaultSyncContextFactory.class);
      this.addService(RepositoryEventDispatcher.class, DefaultRepositoryEventDispatcher.class);
      this.addService(OfflineController.class, DefaultOfflineController.class);
      this.addService(LocalRepositoryProvider.class, DefaultLocalRepositoryProvider.class);
      this.addService(LocalRepositoryManagerFactory.class, SimpleLocalRepositoryManagerFactory.class);
      this.addService(LocalRepositoryManagerFactory.class, EnhancedLocalRepositoryManagerFactory.class);
      this.addService(LoggerFactory.class, Slf4jLoggerFactory.class);
   }

   private DefaultServiceLocator$Entry getEntry(Class var1, boolean var2) {
      DefaultServiceLocator$Entry var3 = (DefaultServiceLocator$Entry)this.entries.get(Objects.requireNonNull(var1, "service type cannot be null"));
      if (var3 == null && var2) {
         var3 = new DefaultServiceLocator$Entry(this, var1);
         this.entries.put(var1, var3);
      }

      return var3;
   }

   public DefaultServiceLocator setService(Class var1, Class var2) {
      this.getEntry(var1, true).setService(var2);
      return this;
   }

   public DefaultServiceLocator addService(Class var1, Class var2) {
      this.getEntry(var1, true).addService(var2);
      return this;
   }

   public DefaultServiceLocator setServices(Class var1, Object... var2) {
      this.getEntry(var1, true).setServices(var2);
      return this;
   }

   public Object getService(Class var1) {
      DefaultServiceLocator$Entry var2 = this.getEntry(var1, false);
      return var2 != null ? var2.getInstance() : null;
   }

   public List getServices(Class var1) {
      DefaultServiceLocator$Entry var2 = this.getEntry(var1, false);
      return var2 != null ? var2.getInstances() : null;
   }

   private void serviceCreationFailed(Class var1, Class var2, Throwable var3) {
      if (this.errorHandler != null) {
         this.errorHandler.serviceCreationFailed(var1, var2, var3);
      }

   }

   public void setErrorHandler(DefaultServiceLocator$ErrorHandler var1) {
      this.errorHandler = var1;
   }

   static void access$000(DefaultServiceLocator var0, Class var1, Class var2, Throwable var3) {
      var0.serviceCreationFailed(var1, var2, var3);
   }
}
