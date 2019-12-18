package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;

final class DataPool$BadDescriptor extends DataPool$Descriptor {
   static final DataPool$BadDescriptor INSTANCE = new DataPool$BadDescriptor();

   DataPool$BadDescriptor() {
      super();
   }

   public ArtifactDescriptorResult toResult(ArtifactDescriptorRequest var1) {
      return DataPool.NO_DESCRIPTOR;
   }
}
