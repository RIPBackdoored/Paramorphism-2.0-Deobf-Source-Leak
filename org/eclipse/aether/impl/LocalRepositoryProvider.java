package org.eclipse.aether.impl;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.LocalRepositoryManager;
import org.eclipse.aether.repository.NoLocalRepositoryManagerException;

public interface LocalRepositoryProvider {
   LocalRepositoryManager newLocalRepositoryManager(RepositorySystemSession var1, LocalRepository var2) throws NoLocalRepositoryManagerException;
}
