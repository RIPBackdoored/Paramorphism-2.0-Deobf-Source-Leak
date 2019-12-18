package org.eclipse.aether.transport.file;

import javax.inject.Named;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.spi.connector.transport.Transporter;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transfer.NoTransporterException;

@Named("file")
public final class FileTransporterFactory implements TransporterFactory {
   private float priority;

   public FileTransporterFactory() {
      super();
   }

   public float getPriority() {
      return this.priority;
   }

   public FileTransporterFactory setPriority(float var1) {
      this.priority = var1;
      return this;
   }

   public Transporter newInstance(RepositorySystemSession var1, RemoteRepository var2) throws NoTransporterException {
      return new FileTransporter(var2);
   }
}
