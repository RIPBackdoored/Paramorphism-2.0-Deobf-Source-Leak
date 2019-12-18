package org.eclipse.aether;

import org.eclipse.aether.artifact.ArtifactType;
import org.eclipse.aether.artifact.ArtifactTypeRegistry;

final class DefaultRepositorySystemSession$NullArtifactTypeRegistry implements ArtifactTypeRegistry {
   public static final ArtifactTypeRegistry INSTANCE = new DefaultRepositorySystemSession$NullArtifactTypeRegistry();

   DefaultRepositorySystemSession$NullArtifactTypeRegistry() {
      super();
   }

   public ArtifactType get(String var1) {
      return null;
   }
}
