package org.eclipse.aether.impl;

import java.util.Collection;
import org.eclipse.aether.artifact.Artifact;

public interface MetadataGenerator {
   Collection prepare(Collection var1);

   Artifact transformArtifact(Artifact var1);

   Collection finish(Collection var1);
}
