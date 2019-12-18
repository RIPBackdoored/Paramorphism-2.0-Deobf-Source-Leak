package org.eclipse.aether.internal.impl;

import org.eclipse.aether.spi.localrepo.*;
import javax.inject.*;
import org.eclipse.aether.*;
import org.eclipse.aether.repository.*;

@Named("enhanced")
public class EnhancedLocalRepositoryManagerFactory implements LocalRepositoryManagerFactory
{
    private float priority;
    
    public EnhancedLocalRepositoryManagerFactory() {
        super();
        this.priority = 10.0f;
    }
    
    @Override
    public LocalRepositoryManager newInstance(final RepositorySystemSession repositorySystemSession, final LocalRepository localRepository) throws NoLocalRepositoryManagerException {
        if ("".equals(localRepository.getContentType()) || "default".equals(localRepository.getContentType())) {
            return new EnhancedLocalRepositoryManager(localRepository.getBasedir(), repositorySystemSession);
        }
        throw new NoLocalRepositoryManagerException(localRepository);
    }
    
    @Override
    public float getPriority() {
        return this.priority;
    }
    
    public EnhancedLocalRepositoryManagerFactory setPriority(final float priority) {
        this.priority = priority;
        return this;
    }
}
