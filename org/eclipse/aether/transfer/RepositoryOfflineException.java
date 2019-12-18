package org.eclipse.aether.transfer;

import org.eclipse.aether.*;
import org.eclipse.aether.repository.*;

public class RepositoryOfflineException extends RepositoryException
{
    private final transient RemoteRepository repository;
    
    private static String getMessage(final RemoteRepository remoteRepository) {
        if (remoteRepository == null) {
            return "Cannot access remote repositories in offline mode";
        }
        return "Cannot access " + remoteRepository.getId() + " (" + remoteRepository.getUrl() + ") in offline mode";
    }
    
    public RepositoryOfflineException(final RemoteRepository repository) {
        super(getMessage(repository));
        this.repository = repository;
    }
    
    public RepositoryOfflineException(final RemoteRepository repository, final String s) {
        super(s);
        this.repository = repository;
    }
    
    public RemoteRepository getRepository() {
        return this.repository;
    }
}
