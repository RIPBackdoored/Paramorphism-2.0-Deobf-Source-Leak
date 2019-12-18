package org.eclipse.aether;

public interface RepositoryListener {
   void artifactDescriptorInvalid(RepositoryEvent var1);

   void artifactDescriptorMissing(RepositoryEvent var1);

   void metadataInvalid(RepositoryEvent var1);

   void artifactResolving(RepositoryEvent var1);

   void artifactResolved(RepositoryEvent var1);

   void metadataResolving(RepositoryEvent var1);

   void metadataResolved(RepositoryEvent var1);

   void artifactDownloading(RepositoryEvent var1);

   void artifactDownloaded(RepositoryEvent var1);

   void metadataDownloading(RepositoryEvent var1);

   void metadataDownloaded(RepositoryEvent var1);

   void artifactInstalling(RepositoryEvent var1);

   void artifactInstalled(RepositoryEvent var1);

   void metadataInstalling(RepositoryEvent var1);

   void metadataInstalled(RepositoryEvent var1);

   void artifactDeploying(RepositoryEvent var1);

   void artifactDeployed(RepositoryEvent var1);

   void metadataDeploying(RepositoryEvent var1);

   void metadataDeployed(RepositoryEvent var1);
}
