package org.eclipse.aether.internal.impl.collect;

import java.util.Collection;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;

final class DataPool$GoodDescriptor extends DataPool$Descriptor {
   final Artifact artifact;
   final List relocations;
   final Collection aliases;
   final List repositories;
   final List dependencies;
   final List managedDependencies;

   DataPool$GoodDescriptor(ArtifactDescriptorResult var1) {
      super();
      this.artifact = var1.getArtifact();
      this.relocations = var1.getRelocations();
      this.aliases = var1.getAliases();
      this.dependencies = var1.getDependencies();
      this.managedDependencies = var1.getManagedDependencies();
      this.repositories = var1.getRepositories();
   }

   public ArtifactDescriptorResult toResult(ArtifactDescriptorRequest var1) {
      ArtifactDescriptorResult var2 = new ArtifactDescriptorResult(var1);
      var2.setArtifact(this.artifact);
      var2.setRelocations(this.relocations);
      var2.setAliases(this.aliases);
      var2.setDependencies(this.dependencies);
      var2.setManagedDependencies(this.managedDependencies);
      var2.setRepositories(this.repositories);
      return var2;
   }
}
