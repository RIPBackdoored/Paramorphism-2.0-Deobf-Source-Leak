package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;

abstract class DataPool$Descriptor {
   DataPool$Descriptor() {
      super();
   }

   public abstract ArtifactDescriptorResult toResult(ArtifactDescriptorRequest var1);
}
