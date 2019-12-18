package org.eclipse.aether.resolution;

import org.eclipse.aether.RepositorySystemSession;

public interface ArtifactDescriptorPolicy {
   int STRICT = 0;
   int IGNORE_MISSING = 1;
   int IGNORE_INVALID = 2;
   int IGNORE_ERRORS = 3;

   int getPolicy(RepositorySystemSession var1, ArtifactDescriptorPolicyRequest var2);
}
