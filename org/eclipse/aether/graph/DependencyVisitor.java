package org.eclipse.aether.graph;

public interface DependencyVisitor
{
    boolean visitEnter(final DependencyNode p0);
    
    boolean visitLeave(final DependencyNode p0);
}
