package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.artifact.Artifact;

class ConflictMarker$Key {
   private final Artifact artifact;

   ConflictMarker$Key(Artifact var1) {
      super();
      this.artifact = var1;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ConflictMarker$Key)) {
         return false;
      } else {
         ConflictMarker$Key var2 = (ConflictMarker$Key)var1;
         return this.artifact.getArtifactId().equals(var2.artifact.getArtifactId()) && this.artifact.getGroupId().equals(var2.artifact.getGroupId()) && this.artifact.getExtension().equals(var2.artifact.getExtension()) && this.artifact.getClassifier().equals(var2.artifact.getClassifier());
      }
   }

   public int hashCode() {
      byte var1 = 17;
      int var2 = var1 * 31 + this.artifact.getArtifactId().hashCode();
      var2 = var2 * 31 + this.artifact.getGroupId().hashCode();
      var2 = var2 * 31 + this.artifact.getClassifier().hashCode();
      var2 = var2 * 31 + this.artifact.getExtension().hashCode();
      return var2;
   }

   public String toString() {
      return this.artifact.getGroupId() + ':' + this.artifact.getArtifactId() + ':' + this.artifact.getClassifier() + ':' + this.artifact.getExtension();
   }
}
