package org.eclipse.aether.resolution;

import org.eclipse.aether.artifact.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.*;
import java.util.*;

public final class ArtifactDescriptorRequest
{
    private Artifact artifact;
    private List<RemoteRepository> repositories;
    private String context;
    private RequestTrace trace;
    
    public ArtifactDescriptorRequest() {
        super();
        this.repositories = Collections.emptyList();
        this.context = "";
    }
    
    public ArtifactDescriptorRequest(final Artifact artifact, final List<RemoteRepository> repositories, final String requestContext) {
        super();
        this.repositories = Collections.emptyList();
        this.context = "";
        this.setArtifact(artifact);
        this.setRepositories(repositories);
        this.setRequestContext(requestContext);
    }
    
    public Artifact getArtifact() {
        return this.artifact;
    }
    
    public ArtifactDescriptorRequest setArtifact(final Artifact artifact) {
        this.artifact = artifact;
        return this;
    }
    
    public List<RemoteRepository> getRepositories() {
        return this.repositories;
    }
    
    public ArtifactDescriptorRequest setRepositories(final List<RemoteRepository> repositories) {
        if (repositories == null) {
            this.repositories = Collections.emptyList();
        }
        else {
            this.repositories = repositories;
        }
        return this;
    }
    
    public ArtifactDescriptorRequest addRepository(final RemoteRepository remoteRepository) {
        if (remoteRepository != null) {
            if (this.repositories.isEmpty()) {
                this.repositories = new ArrayList<RemoteRepository>();
            }
            this.repositories.add(remoteRepository);
        }
        return this;
    }
    
    public String getRequestContext() {
        return this.context;
    }
    
    public ArtifactDescriptorRequest setRequestContext(final String s) {
        this.context = ((s != null) ? s : "");
        return this;
    }
    
    public RequestTrace getTrace() {
        return this.trace;
    }
    
    public ArtifactDescriptorRequest setTrace(final RequestTrace trace) {
        this.trace = trace;
        return this;
    }
    
    @Override
    public String toString() {
        return this.getArtifact() + " < " + this.getRepositories();
    }
}
