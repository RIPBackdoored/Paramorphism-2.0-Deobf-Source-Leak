package org.eclipse.aether.internal.impl;

import java.io.File;
import org.eclipse.aether.RepositoryEvent$Builder;
import org.eclipse.aether.RepositoryEvent$EventType;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.impl.RepositoryEventDispatcher;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.transfer.ArtifactTransferException;

final class DefaultDeployer$EventCatapult {
   private final RepositorySystemSession session;
   private final RequestTrace trace;
   private final RemoteRepository repository;
   private final RepositoryEventDispatcher dispatcher;

   DefaultDeployer$EventCatapult(RepositorySystemSession var1, RequestTrace var2, RemoteRepository var3, RepositoryEventDispatcher var4) {
      super();
      this.session = var1;
      this.trace = var2;
      this.repository = var3;
      this.dispatcher = var4;
   }

   public RepositorySystemSession getSession() {
      return this.session;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public void artifactDeploying(Artifact var1, File var2) {
      RepositoryEvent$Builder var3 = new RepositoryEvent$Builder(this.session, RepositoryEvent$EventType.ARTIFACT_DEPLOYING);
      var3.setTrace(this.trace);
      var3.setArtifact(var1);
      var3.setRepository(this.repository);
      var3.setFile(var2);
      this.dispatcher.dispatch(var3.build());
   }

   public void artifactDeployed(Artifact var1, File var2, ArtifactTransferException var3) {
      RepositoryEvent$Builder var4 = new RepositoryEvent$Builder(this.session, RepositoryEvent$EventType.ARTIFACT_DEPLOYED);
      var4.setTrace(this.trace);
      var4.setArtifact(var1);
      var4.setRepository(this.repository);
      var4.setFile(var2);
      var4.setException(var3);
      this.dispatcher.dispatch(var4.build());
   }

   public void metadataDeploying(Metadata var1, File var2) {
      RepositoryEvent$Builder var3 = new RepositoryEvent$Builder(this.session, RepositoryEvent$EventType.METADATA_DEPLOYING);
      var3.setTrace(this.trace);
      var3.setMetadata(var1);
      var3.setRepository(this.repository);
      var3.setFile(var2);
      this.dispatcher.dispatch(var3.build());
   }

   public void metadataDeployed(Metadata var1, File var2, Exception var3) {
      RepositoryEvent$Builder var4 = new RepositoryEvent$Builder(this.session, RepositoryEvent$EventType.METADATA_DEPLOYED);
      var4.setTrace(this.trace);
      var4.setMetadata(var1);
      var4.setRepository(this.repository);
      var4.setFile(var2);
      var4.setException(var3);
      this.dispatcher.dispatch(var4.build());
   }
}
