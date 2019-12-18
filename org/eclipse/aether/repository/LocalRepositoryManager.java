package org.eclipse.aether.repository;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;

public interface LocalRepositoryManager {
   LocalRepository getRepository();

   String getPathForLocalArtifact(Artifact var1);

   String getPathForRemoteArtifact(Artifact var1, RemoteRepository var2, String var3);

   String getPathForLocalMetadata(Metadata var1);

   String getPathForRemoteMetadata(Metadata var1, RemoteRepository var2, String var3);

   LocalArtifactResult find(RepositorySystemSession var1, LocalArtifactRequest var2);

   void add(RepositorySystemSession var1, LocalArtifactRegistration var2);

   LocalMetadataResult find(RepositorySystemSession var1, LocalMetadataRequest var2);

   void add(RepositorySystemSession var1, LocalMetadataRegistration var2);
}
