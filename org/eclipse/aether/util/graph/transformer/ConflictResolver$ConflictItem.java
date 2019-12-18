package org.eclipse.aether.util.graph.transformer;

import org.eclipse.aether.artifact.*;
import org.eclipse.aether.graph.*;
import java.util.*;

public static final class ConflictItem
{
    final List<DependencyNode> parent;
    final Artifact artifact;
    final DependencyNode node;
    int depth;
    Object scopes;
    int optionalities;
    public static final int OPTIONAL_FALSE = 1;
    public static final int OPTIONAL_TRUE = 2;
    
    ConflictItem(final DependencyNode dependencyNode, final DependencyNode node, final String scopes, final boolean b) {
        super();
        if (dependencyNode != null) {
            this.parent = dependencyNode.getChildren();
            this.artifact = dependencyNode.getArtifact();
        }
        else {
            this.parent = null;
            this.artifact = null;
        }
        this.node = node;
        this.scopes = scopes;
        this.optionalities = (b ? 2 : 1);
    }
    
    public ConflictItem(final DependencyNode dependencyNode, final DependencyNode node, final int depth, final int optionalities, final String... array) {
        super();
        this.parent = ((dependencyNode != null) ? dependencyNode.getChildren() : null);
        this.artifact = ((dependencyNode != null) ? dependencyNode.getArtifact() : null);
        this.node = node;
        this.depth = depth;
        this.optionalities = optionalities;
        this.scopes = Arrays.asList(array);
    }
    
    public boolean isSibling(final ConflictItem conflictItem) {
        return this.parent == conflictItem.parent;
    }
    
    public DependencyNode getNode() {
        return this.node;
    }
    
    public Dependency getDependency() {
        return this.node.getDependency();
    }
    
    public int getDepth() {
        return this.depth;
    }
    
    public Collection<String> getScopes() {
        if (this.scopes instanceof String) {
            return Collections.singleton(this.scopes);
        }
        return (Collection<String>)this.scopes;
    }
    
    void addScope(final String s) {
        if (this.scopes instanceof Collection) {
            ((Collection)this.scopes).add(s);
        }
        else if (!this.scopes.equals(s)) {
            final HashSet<Object> scopes = new HashSet<Object>();
            scopes.add(this.scopes);
            scopes.add(s);
            this.scopes = scopes;
        }
    }
    
    public int getOptionalities() {
        return this.optionalities;
    }
    
    void addOptional(final boolean b) {
        this.optionalities |= (b ? 2 : 1);
    }
    
    @Override
    public String toString() {
        return this.node + " @ " + this.depth + " < " + this.artifact;
    }
}
