package org.eclipse.aether;

import java.util.Collection;
import java.util.Collections;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.transform.FileTransformerManager;

final class DefaultRepositorySystemSession$NullFileTransformerManager implements FileTransformerManager {
   public static final FileTransformerManager INSTANCE = new DefaultRepositorySystemSession$NullFileTransformerManager();

   DefaultRepositorySystemSession$NullFileTransformerManager() {
      super();
   }

   public Collection getTransformersForArtifact(Artifact var1) {
      return Collections.emptyList();
   }
}
