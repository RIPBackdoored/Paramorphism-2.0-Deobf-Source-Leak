package org.eclipse.aether.spi.connector.layout;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.transfer.NoRepositoryLayoutException;

public interface RepositoryLayoutProvider {
   RepositoryLayout newRepositoryLayout(RepositorySystemSession var1, RemoteRepository var2) throws NoRepositoryLayoutException;
}
