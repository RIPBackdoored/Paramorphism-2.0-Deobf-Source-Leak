package org.eclipse.aether.util.graph.manager;

import org.eclipse.aether.artifact.Artifact;

class ClassicDependencyManager$Key {
   private final Artifact artifact;
   private final int hashCode;

   ClassicDependencyManager$Key(Artifact var1) {
      super();
      this.artifact = var1;
      byte var2 = 17;
      int var3 = var2 * 31 + var1.getGroupId().hashCode();
      var3 = var3 * 31 + var1.getArtifactId().hashCode();
      this.hashCode = var3;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ClassicDependencyManager$Key)) {
         return false;
      } else {
         ClassicDependencyManager$Key var2 = (ClassicDependencyManager$Key)var1;
         return this.artifact.getArtifactId().equals(var2.artifact.getArtifactId()) && this.artifact.getGroupId().equals(var2.artifact.getGroupId()) && this.artifact.getExtension().equals(var2.artifact.getExtension()) && this.artifact.getClassifier().equals(var2.artifact.getClassifier());
      }
   }

   public int hashCode() {
      return this.hashCode;
   }
}
