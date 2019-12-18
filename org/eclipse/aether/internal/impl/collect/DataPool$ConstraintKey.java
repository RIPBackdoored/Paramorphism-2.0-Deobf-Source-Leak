package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.artifact.*;
import java.util.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.resolution.*;

static final class ConstraintKey
{
    private final Artifact artifact;
    private final List<RemoteRepository> repositories;
    private final int hashCode;
    
    ConstraintKey(final VersionRangeRequest versionRangeRequest) {
        super();
        this.artifact = versionRangeRequest.getArtifact();
        this.repositories = versionRangeRequest.getRepositories();
        this.hashCode = this.artifact.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConstraintKey)) {
            return false;
        }
        final ConstraintKey constraintKey = (ConstraintKey)o;
        return this.artifact.equals(constraintKey.artifact) && equals(this.repositories, constraintKey.repositories);
    }
    
    private static boolean equals(final List<RemoteRepository> list, final List<RemoteRepository> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            final RemoteRepository remoteRepository = list.get(i);
            final RemoteRepository remoteRepository2 = list2.get(i);
            if (remoteRepository.isRepositoryManager() != remoteRepository2.isRepositoryManager()) {
                return false;
            }
            if (remoteRepository.isRepositoryManager()) {
                if (!equals(remoteRepository.getMirroredRepositories(), remoteRepository2.getMirroredRepositories())) {
                    return false;
                }
            }
            else {
                if (!remoteRepository.getUrl().equals(remoteRepository2.getUrl())) {
                    return false;
                }
                if (remoteRepository.getPolicy(true).isEnabled() != remoteRepository2.getPolicy(true).isEnabled()) {
                    return false;
                }
                if (remoteRepository.getPolicy(false).isEnabled() != remoteRepository2.getPolicy(false).isEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
