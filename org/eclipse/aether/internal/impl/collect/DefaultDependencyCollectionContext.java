package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.collection.*;
import org.eclipse.aether.*;
import org.eclipse.aether.artifact.*;
import org.eclipse.aether.graph.*;
import java.util.*;

final class DefaultDependencyCollectionContext implements DependencyCollectionContext
{
    private final RepositorySystemSession session;
    private Artifact artifact;
    private Dependency dependency;
    private List<Dependency> managedDependencies;
    
    DefaultDependencyCollectionContext(final RepositorySystemSession session, final Artifact artifact, final Dependency dependency, final List<Dependency> managedDependencies) {
        super();
        this.session = session;
        this.artifact = ((dependency != null) ? dependency.getArtifact() : artifact);
        this.dependency = dependency;
        this.managedDependencies = managedDependencies;
    }
    
    @Override
    public RepositorySystemSession getSession() {
        return this.session;
    }
    
    @Override
    public Artifact getArtifact() {
        return this.artifact;
    }
    
    @Override
    public Dependency getDependency() {
        return this.dependency;
    }
    
    @Override
    public List<Dependency> getManagedDependencies() {
        return this.managedDependencies;
    }
    
    public void set(final Dependency dependency, final List<Dependency> managedDependencies) {
        this.artifact = dependency.getArtifact();
        this.dependency = dependency;
        this.managedDependencies = managedDependencies;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getDependency());
    }
}
