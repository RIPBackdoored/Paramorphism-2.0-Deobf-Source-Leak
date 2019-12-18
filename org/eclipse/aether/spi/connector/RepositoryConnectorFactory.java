package org.eclipse.aether.spi.connector;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.transfer.NoRepositoryConnectorException;

public interface RepositoryConnectorFactory {
   RepositoryConnector newInstance(RepositorySystemSession var1, RemoteRepository var2) throws NoRepositoryConnectorException;

   float getPriority();
}
