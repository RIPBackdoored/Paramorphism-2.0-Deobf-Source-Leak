package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.util.ConfigUtils;

class DefaultDependencyCollector$Args {
   final RepositorySystemSession session;
   final boolean ignoreRepos;
   final boolean premanagedState;
   final RequestTrace trace;
   final DataPool pool;
   final NodeStack nodes;
   final DefaultDependencyCollectionContext collectionContext;
   final DefaultVersionFilterContext versionContext;
   final CollectRequest request;

   DefaultDependencyCollector$Args(RepositorySystemSession var1, RequestTrace var2, DataPool var3, NodeStack var4, DefaultDependencyCollectionContext var5, DefaultVersionFilterContext var6, CollectRequest var7) {
      super();
      this.session = var1;
      this.request = var7;
      this.ignoreRepos = var1.isIgnoreArtifactDescriptorRepositories();
      this.premanagedState = ConfigUtils.getBoolean(var1, false, "aether.dependencyManager.verbose");
      this.trace = var2;
      this.pool = var3;
      this.nodes = var4;
      this.collectionContext = var5;
      this.versionContext = var6;
   }
}
