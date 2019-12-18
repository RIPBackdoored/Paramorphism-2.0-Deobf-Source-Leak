package org.eclipse.aether.util.graph.visitor;

import org.eclipse.aether.graph.*;
import java.util.*;
import org.eclipse.aether.artifact.*;
import java.io.*;

abstract class AbstractDepthFirstNodeListGenerator implements DependencyVisitor
{
    private final Map<DependencyNode, Object> visitedNodes;
    protected final List<DependencyNode> nodes;
    
    AbstractDepthFirstNodeListGenerator() {
        super();
        this.nodes = new ArrayList<DependencyNode>(128);
        this.visitedNodes = new IdentityHashMap<DependencyNode, Object>(512);
    }
    
    public List<DependencyNode> getNodes() {
        return this.nodes;
    }
    
    public List<Dependency> getDependencies(final boolean b) {
        final ArrayList<Dependency> list = new ArrayList<Dependency>(this.getNodes().size());
        final Iterator<DependencyNode> iterator = this.getNodes().iterator();
        while (iterator.hasNext()) {
            final Dependency dependency = iterator.next().getDependency();
            if (dependency != null && (b || dependency.getArtifact().getFile() != null)) {
                list.add(dependency);
            }
        }
        return list;
    }
    
    public List<Artifact> getArtifacts(final boolean b) {
        final ArrayList<Artifact> list = new ArrayList<Artifact>(this.getNodes().size());
        for (final DependencyNode dependencyNode : this.getNodes()) {
            if (dependencyNode.getDependency() != null) {
                final Artifact artifact = dependencyNode.getDependency().getArtifact();
                if (!b && artifact.getFile() == null) {
                    continue;
                }
                list.add(artifact);
            }
        }
        return list;
    }
    
    public List<File> getFiles() {
        final ArrayList<File> list = new ArrayList<File>(this.getNodes().size());
        for (final DependencyNode dependencyNode : this.getNodes()) {
            if (dependencyNode.getDependency() != null) {
                final File file = dependencyNode.getDependency().getArtifact().getFile();
                if (file == null) {
                    continue;
                }
                list.add(file);
            }
        }
        return list;
    }
    
    public String getClassPath() {
        final StringBuilder sb = new StringBuilder(1024);
        final Iterator<DependencyNode> iterator = this.getNodes().iterator();
        while (iterator.hasNext()) {
            final DependencyNode dependencyNode = iterator.next();
            if (dependencyNode.getDependency() != null) {
                final Artifact artifact = dependencyNode.getDependency().getArtifact();
                if (artifact.getFile() == null) {
                    continue;
                }
                sb.append(artifact.getFile().getAbsolutePath());
                if (!iterator.hasNext()) {
                    continue;
                }
                sb.append(File.pathSeparatorChar);
            }
        }
        return sb.toString();
    }
    
    protected boolean setVisited(final DependencyNode dependencyNode) {
        return this.visitedNodes.put(dependencyNode, Boolean.TRUE) == null;
    }
    
    @Override
    public abstract boolean visitEnter(final DependencyNode p0);
    
    @Override
    public abstract boolean visitLeave(final DependencyNode p0);
}
