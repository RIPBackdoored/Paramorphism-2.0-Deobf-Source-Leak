package org.eclipse.aether.spi.connector;

import java.io.File;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.transfer.ArtifactTransferException;

public abstract class ArtifactTransfer extends Transfer {
   private Artifact artifact;
   private File file;
   private ArtifactTransferException exception;

   ArtifactTransfer() {
      super();
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public ArtifactTransfer setArtifact(Artifact var1) {
      this.artifact = var1;
      return this;
   }

   public File getFile() {
      return this.file;
   }

   public ArtifactTransfer setFile(File var1) {
      this.file = var1;
      return this;
   }

   public ArtifactTransferException getException() {
      return this.exception;
   }

   public ArtifactTransfer setException(ArtifactTransferException var1) {
      this.exception = var1;
      return this;
   }

   public Exception getException() {
      return this.getException();
   }
}
