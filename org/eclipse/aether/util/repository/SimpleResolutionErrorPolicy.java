package org.eclipse.aether.util.repository;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.resolution.ResolutionErrorPolicy;
import org.eclipse.aether.resolution.ResolutionErrorPolicyRequest;

public final class SimpleResolutionErrorPolicy implements ResolutionErrorPolicy {
   private final int artifactPolicy;
   private final int metadataPolicy;

   public SimpleResolutionErrorPolicy(boolean var1, boolean var2) {
      this((var1 ? 1 : 0) | (var2 ? 2 : 0));
   }

   public SimpleResolutionErrorPolicy(int var1) {
      this(var1, var1);
   }

   public SimpleResolutionErrorPolicy(int var1, int var2) {
      super();
      this.artifactPolicy = var1;
      this.metadataPolicy = var2;
   }

   public int getArtifactPolicy(RepositorySystemSession var1, ResolutionErrorPolicyRequest var2) {
      return this.artifactPolicy;
   }

   public int getMetadataPolicy(RepositorySystemSession var1, ResolutionErrorPolicyRequest var2) {
      return this.metadataPolicy;
   }
}
