package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.graph.*;
import org.eclipse.aether.collection.*;
import org.eclipse.aether.*;

public abstract static class VersionSelector
{
    public VersionSelector() {
        super();
    }
    
    public VersionSelector getInstance(final DependencyNode dependencyNode, final DependencyGraphTransformationContext dependencyGraphTransformationContext) throws RepositoryException {
        return this;
    }
    
    public abstract void selectVersion(final ConflictContext p0) throws RepositoryException;
}
