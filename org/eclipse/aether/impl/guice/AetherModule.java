package org.eclipse.aether.impl.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Singleton;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.impl.ArtifactResolver;
import org.eclipse.aether.impl.DependencyCollector;
import org.eclipse.aether.impl.Deployer;
import org.eclipse.aether.impl.Installer;
import org.eclipse.aether.impl.LocalRepositoryProvider;
import org.eclipse.aether.impl.MetadataResolver;
import org.eclipse.aether.impl.OfflineController;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.impl.RepositoryConnectorProvider;
import org.eclipse.aether.impl.RepositoryEventDispatcher;
import org.eclipse.aether.impl.SyncContextFactory;
import org.eclipse.aether.impl.UpdateCheckManager;
import org.eclipse.aether.impl.UpdatePolicyAnalyzer;
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
import org.eclipse.aether.spi.connector.checksum.ChecksumPolicyProvider;
import org.eclipse.aether.spi.connector.layout.RepositoryLayoutFactory;
import org.eclipse.aether.spi.connector.layout.RepositoryLayoutProvider;
import org.eclipse.aether.spi.connector.transport.TransporterProvider;
import org.eclipse.aether.spi.io.FileProcessor;
import org.eclipse.aether.spi.localrepo.LocalRepositoryManagerFactory;

public class AetherModule extends AbstractModule {
   public AetherModule() {
      super();
   }

   protected void configure() {
      this.bind(RepositorySystem.class).to(DefaultRepositorySystem.class).in(Singleton.class);
      this.bind(ArtifactResolver.class).to(DefaultArtifactResolver.class).in(Singleton.class);
      this.bind(DependencyCollector.class).to(DefaultDependencyCollector.class).in(Singleton.class);
      this.bind(Deployer.class).to(DefaultDeployer.class).in(Singleton.class);
      this.bind(Installer.class).to(DefaultInstaller.class).in(Singleton.class);
      this.bind(MetadataResolver.class).to(DefaultMetadataResolver.class).in(Singleton.class);
      this.bind(RepositoryLayoutProvider.class).to(DefaultRepositoryLayoutProvider.class).in(Singleton.class);
      this.bind(RepositoryLayoutFactory.class).annotatedWith(Names.named("maven2")).to(Maven2RepositoryLayoutFactory.class).in(Singleton.class);
      this.bind(TransporterProvider.class).to(DefaultTransporterProvider.class).in(Singleton.class);
      this.bind(ChecksumPolicyProvider.class).to(DefaultChecksumPolicyProvider.class).in(Singleton.class);
      this.bind(RepositoryConnectorProvider.class).to(DefaultRepositoryConnectorProvider.class).in(Singleton.class);
      this.bind(RemoteRepositoryManager.class).to(DefaultRemoteRepositoryManager.class).in(Singleton.class);
      this.bind(UpdateCheckManager.class).to(DefaultUpdateCheckManager.class).in(Singleton.class);
      this.bind(UpdatePolicyAnalyzer.class).to(DefaultUpdatePolicyAnalyzer.class).in(Singleton.class);
      this.bind(FileProcessor.class).to(DefaultFileProcessor.class).in(Singleton.class);
      this.bind(SyncContextFactory.class).to(DefaultSyncContextFactory.class).in(Singleton.class);
      this.bind(RepositoryEventDispatcher.class).to(DefaultRepositoryEventDispatcher.class).in(Singleton.class);
      this.bind(OfflineController.class).to(DefaultOfflineController.class).in(Singleton.class);
      this.bind(LocalRepositoryProvider.class).to(DefaultLocalRepositoryProvider.class).in(Singleton.class);
      this.bind(LocalRepositoryManagerFactory.class).annotatedWith(Names.named("simple")).to(SimpleLocalRepositoryManagerFactory.class).in(Singleton.class);
      this.bind(LocalRepositoryManagerFactory.class).annotatedWith(Names.named("enhanced")).to(EnhancedLocalRepositoryManagerFactory.class).in(Singleton.class);
      this.install(new AetherModule$Slf4jModule((AetherModule$1)null));
   }

   @Provides
   @Singleton
   Set provideLocalRepositoryManagerFactories(@Named("simple") LocalRepositoryManagerFactory var1, @Named("enhanced") LocalRepositoryManagerFactory var2) {
      HashSet var3 = new HashSet();
      var3.add(var1);
      var3.add(var2);
      return Collections.unmodifiableSet(var3);
   }

   @Provides
   @Singleton
   Set provideRepositoryLayoutFactories(@Named("maven2") RepositoryLayoutFactory var1) {
      HashSet var2 = new HashSet();
      var2.add(var1);
      return Collections.unmodifiableSet(var2);
   }

   @Provides
   @Singleton
   Set providesRepositoryListeners() {
      return Collections.emptySet();
   }
}
