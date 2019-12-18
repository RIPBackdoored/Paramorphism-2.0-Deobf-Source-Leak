package org.eclipse.aether.internal.impl;

import org.eclipse.aether.repository.*;
import java.util.*;

static class ResolutionGroup
{
    final RemoteRepository repository;
    final List<ResolutionItem> items;
    
    ResolutionGroup(final RemoteRepository repository) {
        super();
        this.items = new ArrayList<ResolutionItem>();
        this.repository = repository;
    }
    
    boolean matches(final RemoteRepository remoteRepository) {
        return this.repository.getUrl().equals(remoteRepository.getUrl()) && this.repository.getContentType().equals(remoteRepository.getContentType()) && this.repository.isRepositoryManager() == remoteRepository.isRepositoryManager();
    }
}
