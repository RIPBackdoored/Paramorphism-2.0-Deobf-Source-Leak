package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.graph.*;
import org.eclipse.aether.collection.*;
import org.eclipse.aether.*;

public abstract static class ScopeSelector
{
    public ScopeSelector() {
        super();
    }
    
    public ScopeSelector getInstance(final DependencyNode dependencyNode, final DependencyGraphTransformationContext dependencyGraphTransformationContext) throws RepositoryException {
        return this;
    }
    
    public abstract void selectScope(final ConflictContext p0) throws RepositoryException;
}
