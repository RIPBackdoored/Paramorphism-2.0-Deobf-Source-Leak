package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.graph.*;
import org.eclipse.aether.collection.*;
import org.eclipse.aether.*;

public abstract static class ScopeDeriver
{
    public ScopeDeriver() {
        super();
    }
    
    public ScopeDeriver getInstance(final DependencyNode dependencyNode, final DependencyGraphTransformationContext dependencyGraphTransformationContext) throws RepositoryException {
        return this;
    }
    
    public abstract void deriveScope(final ScopeContext p0) throws RepositoryException;
}
