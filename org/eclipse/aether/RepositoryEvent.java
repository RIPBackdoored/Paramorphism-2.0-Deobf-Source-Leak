package org.eclipse.aether;

import java.io.File;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.ArtifactRepository;

public final class RepositoryEvent {
   private final RepositoryEvent$EventType type;
   private final RepositorySystemSession session;
   private final Artifact artifact;
   private final Metadata metadata;
   private final ArtifactRepository repository;
   private final File file;
   private final List exceptions;
   private final RequestTrace trace;

   RepositoryEvent(RepositoryEvent$Builder var1) {
      super();
      this.type = var1.type;
      this.session = var1.session;
      this.artifact = var1.artifact;
      this.metadata = var1.metadata;
      this.repository = var1.repository;
      this.file = var1.file;
      this.exceptions = var1.exceptions;
      this.trace = var1.trace;
   }

   public RepositoryEvent$EventType getType() {
      return this.type;
   }

   public RepositorySystemSession getSession() {
      return this.session;
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public Metadata getMetadata() {
      return this.metadata;
   }

   public File getFile() {
      return this.file;
   }

   public ArtifactRepository getRepository() {
      return this.repository;
   }

   public Exception getException() {
      return this.exceptions.isEmpty() ? null : (Exception)this.exceptions.get(0);
   }

   public List getExceptions() {
      return this.exceptions;
   }

   public RequestTrace getTrace() {
      return this.trace;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(256);
      var1.append(this.getType());
      if (this.getArtifact() != null) {
         var1.append(" ").append(this.getArtifact());
      }

      if (this.getMetadata() != null) {
         var1.append(" ").append(this.getMetadata());
      }

      if (this.getFile() != null) {
         var1.append(" (").append(this.getFile()).append(")");
      }

      if (this.getRepository() != null) {
         var1.append(" @ ").append(this.getRepository());
      }

      return var1.toString();
   }
}
