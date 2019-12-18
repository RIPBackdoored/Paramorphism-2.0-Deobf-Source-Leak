package org.eclipse.aether.impl;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.transfer.RepositoryOfflineException;

public interface OfflineController {
   void checkOffline(RepositorySystemSession var1, RemoteRepository var2) throws RepositoryOfflineException;
}
