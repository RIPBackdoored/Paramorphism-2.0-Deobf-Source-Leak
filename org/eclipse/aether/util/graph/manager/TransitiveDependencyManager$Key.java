package org.eclipse.aether.util.graph.manager;

import java.util.Objects;
import org.eclipse.aether.artifact.Artifact;

class TransitiveDependencyManager$Key {
   private final Artifact artifact;
   private final int hashCode;

   TransitiveDependencyManager$Key(Artifact var1) {
      super();
      this.artifact = var1;
      this.hashCode = Objects.hash(new Object[]{var1.getGroupId(), var1.getArtifactId()});
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof TransitiveDependencyManager$Key;
      if (!var2) {
         return var2;
      } else {
         TransitiveDependencyManager$Key var3 = (TransitiveDependencyManager$Key)var1;
         return Objects.equals(this.artifact.getArtifactId(), var3.artifact.getArtifactId()) && Objects.equals(this.artifact.getGroupId(), var3.artifact.getGroupId()) && Objects.equals(this.artifact.getExtension(), var3.artifact.getExtension()) && Objects.equals(this.artifact.getClassifier(), var3.artifact.getClassifier());
      }
   }

   public int hashCode() {
      return this.hashCode;
   }
}
