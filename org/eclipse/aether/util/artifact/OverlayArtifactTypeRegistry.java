package org.eclipse.aether.util.artifact;

import org.eclipse.aether.artifact.ArtifactType;
import org.eclipse.aether.artifact.ArtifactTypeRegistry;

public final class OverlayArtifactTypeRegistry extends SimpleArtifactTypeRegistry {
   private final ArtifactTypeRegistry delegate;

   public OverlayArtifactTypeRegistry(ArtifactTypeRegistry var1) {
      super();
      this.delegate = var1;
   }

   public OverlayArtifactTypeRegistry add(ArtifactType var1) {
      super.add(var1);
      return this;
   }

   public ArtifactType get(String var1) {
      ArtifactType var2 = super.get(var1);
      if (var2 == null && this.delegate != null) {
         var2 = this.delegate.get(var1);
      }

      return var2;
   }

   public String toString() {
      return super.toString();
   }

   public SimpleArtifactTypeRegistry add(ArtifactType var1) {
      return this.add(var1);
   }
}
