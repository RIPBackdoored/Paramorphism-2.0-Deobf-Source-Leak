package org.eclipse.aether.util.artifact;

import org.eclipse.aether.artifact.ArtifactType;

public final class DefaultArtifactTypeRegistry extends SimpleArtifactTypeRegistry {
   public DefaultArtifactTypeRegistry() {
      super();
   }

   public DefaultArtifactTypeRegistry add(ArtifactType var1) {
      super.add(var1);
      return this;
   }

   public String toString() {
      return super.toString();
   }

   public ArtifactType get(String var1) {
      return super.get(var1);
   }

   public SimpleArtifactTypeRegistry add(ArtifactType var1) {
      return this.add(var1);
   }
}
