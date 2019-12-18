package org.eclipse.aether;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.metadata.Metadata;
import org.eclipse.aether.repository.ArtifactRepository;

public final class RepositoryEvent$Builder {
   RepositoryEvent$EventType type;
   RepositorySystemSession session;
   Artifact artifact;
   Metadata metadata;
   ArtifactRepository repository;
   File file;
   List exceptions = Collections.emptyList();
   RequestTrace trace;

   public RepositoryEvent$Builder(RepositorySystemSession var1, RepositoryEvent$EventType var2) {
      super();
      this.session = (RepositorySystemSession)Objects.requireNonNull(var1, "session cannot be null");
      this.type = (RepositoryEvent$EventType)Objects.requireNonNull(var2, "event type cannot be null");
   }

   public RepositoryEvent$Builder setArtifact(Artifact var1) {
      this.artifact = var1;
      return this;
   }

   public RepositoryEvent$Builder setMetadata(Metadata var1) {
      this.metadata = var1;
      return this;
   }

   public RepositoryEvent$Builder setRepository(ArtifactRepository var1) {
      this.repository = var1;
      return this;
   }

   public RepositoryEvent$Builder setFile(File var1) {
      this.file = var1;
      return this;
   }

   public RepositoryEvent$Builder setException(Exception var1) {
      if (var1 != null) {
         this.exceptions = Collections.singletonList(var1);
      } else {
         this.exceptions = Collections.emptyList();
      }

      return this;
   }

   public RepositoryEvent$Builder setExceptions(List var1) {
      if (var1 != null) {
         this.exceptions = var1;
      } else {
         this.exceptions = Collections.emptyList();
      }

      return this;
   }

   public RepositoryEvent$Builder setTrace(RequestTrace var1) {
      this.trace = var1;
      return this;
   }

   public RepositoryEvent build() {
      return new RepositoryEvent(this);
   }
}
