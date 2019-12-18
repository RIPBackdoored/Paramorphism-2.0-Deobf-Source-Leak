package org.eclipse.aether.impl;

import org.eclipse.aether.RepositorySystemSession;

public interface UpdateCheckManager {
   void checkArtifact(RepositorySystemSession var1, UpdateCheck var2);

   void touchArtifact(RepositorySystemSession var1, UpdateCheck var2);

   void checkMetadata(RepositorySystemSession var1, UpdateCheck var2);

   void touchMetadata(RepositorySystemSession var1, UpdateCheck var2);
}
