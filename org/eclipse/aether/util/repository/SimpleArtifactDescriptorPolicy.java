package org.eclipse.aether.util.repository;

import org.eclipse.aether.*;
import org.eclipse.aether.resolution.*;

public final class SimpleArtifactDescriptorPolicy implements ArtifactDescriptorPolicy
{
    private final int policy;
    
    public SimpleArtifactDescriptorPolicy(final boolean b, final boolean b2) {
        this((b ? 1 : 0) | (b2 ? 2 : 0));
    }
    
    public SimpleArtifactDescriptorPolicy(final int policy) {
        super();
        this.policy = policy;
    }
    
    @Override
    public int getPolicy(final RepositorySystemSession repositorySystemSession, final ArtifactDescriptorPolicyRequest artifactDescriptorPolicyRequest) {
        return this.policy;
    }
}
