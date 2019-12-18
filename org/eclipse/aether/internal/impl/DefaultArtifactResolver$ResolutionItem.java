package org.eclipse.aether.internal.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.aether.RequestTrace;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.impl.UpdateCheck;
import org.eclipse.aether.repository.LocalArtifactResult;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.ArtifactDownload;

class DefaultArtifactResolver$ResolutionItem {
   final RequestTrace trace;
   final ArtifactRequest request;
   final ArtifactResult result;
   final LocalArtifactResult local;
   final RemoteRepository repository;
   final Artifact artifact;
   final AtomicBoolean resolved;
   ArtifactDownload download;
   UpdateCheck updateCheck;

   DefaultArtifactResolver$ResolutionItem(RequestTrace var1, Artifact var2, AtomicBoolean var3, ArtifactResult var4, LocalArtifactResult var5, RemoteRepository var6) {
      super();
      this.trace = var1;
      this.artifact = var2;
      this.resolved = var3;
      this.result = var4;
      this.request = var4.getRequest();
      this.local = var5;
      this.repository = var6;
   }
}
