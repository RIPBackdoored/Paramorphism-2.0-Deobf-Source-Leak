package org.eclipse.aether.resolution;

import org.eclipse.aether.artifact.*;

public final class ArtifactDescriptorPolicyRequest
{
    private Artifact artifact;
    private String context;
    
    public ArtifactDescriptorPolicyRequest() {
        super();
        this.context = "";
    }
    
    public ArtifactDescriptorPolicyRequest(final Artifact artifact, final String requestContext) {
        super();
        this.context = "";
        this.setArtifact(artifact);
        this.setRequestContext(requestContext);
    }
    
    public Artifact getArtifact() {
        return this.artifact;
    }
    
    public ArtifactDescriptorPolicyRequest setArtifact(final Artifact artifact) {
        this.artifact = artifact;
        return this;
    }
    
    public String getRequestContext() {
        return this.context;
    }
    
    public ArtifactDescriptorPolicyRequest setRequestContext(final String s) {
        this.context = ((s != null) ? s : "");
        return this;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getArtifact());
    }
}
