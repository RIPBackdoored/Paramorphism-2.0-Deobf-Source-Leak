package org.eclipse.aether.graph;

import org.eclipse.aether.artifact.*;
import org.eclipse.aether.version.*;
import org.eclipse.aether.repository.*;
import java.util.*;

public final class DefaultDependencyNode implements DependencyNode
{
    private List<DependencyNode> children;
    private Dependency dependency;
    private Artifact artifact;
    private List<? extends Artifact> relocations;
    private Collection<? extends Artifact> aliases;
    private VersionConstraint versionConstraint;
    private Version version;
    private byte managedBits;
    private List<RemoteRepository> repositories;
    private String context;
    private Map<Object, Object> data;
    
    public DefaultDependencyNode(final Dependency dependency) {
        super();
        this.dependency = dependency;
        this.artifact = ((dependency != null) ? dependency.getArtifact() : null);
        this.children = new ArrayList<DependencyNode>(0);
        this.aliases = (Collection<? extends Artifact>)Collections.emptyList();
        this.relocations = Collections.emptyList();
        this.repositories = Collections.emptyList();
        this.context = "";
        this.data = Collections.emptyMap();
    }
    
    public DefaultDependencyNode(final Artifact artifact) {
        super();
        this.artifact = artifact;
        this.children = new ArrayList<DependencyNode>(0);
        this.aliases = (Collection<? extends Artifact>)Collections.emptyList();
        this.relocations = Collections.emptyList();
        this.repositories = Collections.emptyList();
        this.context = "";
        this.data = Collections.emptyMap();
    }
    
    public DefaultDependencyNode(final DependencyNode dependencyNode) {
        super();
        this.dependency = dependencyNode.getDependency();
        this.artifact = dependencyNode.getArtifact();
        this.children = new ArrayList<DependencyNode>(0);
        this.setAliases(dependencyNode.getAliases());
        this.setRequestContext(dependencyNode.getRequestContext());
        this.setManagedBits(dependencyNode.getManagedBits());
        this.setRelocations(dependencyNode.getRelocations());
        this.setRepositories(dependencyNode.getRepositories());
        this.setVersion(dependencyNode.getVersion());
        this.setVersionConstraint(dependencyNode.getVersionConstraint());
        final Map<?, ?> data = dependencyNode.getData();
        this.setData(data.isEmpty() ? null : new HashMap<Object, Object>(data));
    }
    
    @Override
    public List<DependencyNode> getChildren() {
        return this.children;
    }
    
    @Override
    public void setChildren(final List<DependencyNode> children) {
        if (children == null) {
            this.children = new ArrayList<DependencyNode>(0);
        }
        else {
            this.children = children;
        }
    }
    
    @Override
    public Dependency getDependency() {
        return this.dependency;
    }
    
    @Override
    public Artifact getArtifact() {
        return this.artifact;
    }
    
    @Override
    public void setArtifact(final Artifact artifact) {
        if (this.dependency == null) {
            throw new IllegalStateException("node does not have a dependency");
        }
        this.dependency = this.dependency.setArtifact(artifact);
        this.artifact = this.dependency.getArtifact();
    }
    
    @Override
    public List<? extends Artifact> getRelocations() {
        return this.relocations;
    }
    
    public void setRelocations(final List<? extends Artifact> relocations) {
        if (relocations == null || relocations.isEmpty()) {
            this.relocations = Collections.emptyList();
        }
        else {
            this.relocations = relocations;
        }
    }
    
    @Override
    public Collection<? extends Artifact> getAliases() {
        return this.aliases;
    }
    
    public void setAliases(final Collection<? extends Artifact> aliases) {
        if (aliases == null || aliases.isEmpty()) {
            this.aliases = (Collection<? extends Artifact>)Collections.emptyList();
        }
        else {
            this.aliases = aliases;
        }
    }
    
    @Override
    public VersionConstraint getVersionConstraint() {
        return this.versionConstraint;
    }
    
    public void setVersionConstraint(final VersionConstraint versionConstraint) {
        this.versionConstraint = versionConstraint;
    }
    
    @Override
    public Version getVersion() {
        return this.version;
    }
    
    public void setVersion(final Version version) {
        this.version = version;
    }
    
    @Override
    public void setScope(final String scope) {
        if (this.dependency == null) {
            throw new IllegalStateException("node does not have a dependency");
        }
        this.dependency = this.dependency.setScope(scope);
    }
    
    @Override
    public void setOptional(final Boolean optional) {
        if (this.dependency == null) {
            throw new IllegalStateException("node does not have a dependency");
        }
        this.dependency = this.dependency.setOptional(optional);
    }
    
    @Override
    public int getManagedBits() {
        return this.managedBits;
    }
    
    public void setManagedBits(final int n) {
        this.managedBits = (byte)(n & 0x1F);
    }
    
    @Override
    public List<RemoteRepository> getRepositories() {
        return this.repositories;
    }
    
    public void setRepositories(final List<RemoteRepository> repositories) {
        if (repositories == null || repositories.isEmpty()) {
            this.repositories = Collections.emptyList();
        }
        else {
            this.repositories = repositories;
        }
    }
    
    @Override
    public String getRequestContext() {
        return this.context;
    }
    
    @Override
    public void setRequestContext(final String s) {
        this.context = ((s != null) ? s : "");
    }
    
    @Override
    public Map<Object, Object> getData() {
        return this.data;
    }
    
    @Override
    public void setData(final Map<Object, Object> data) {
        if (data == null) {
            this.data = Collections.emptyMap();
        }
        else {
            this.data = data;
        }
    }
    
    @Override
    public void setData(final Object o, final Object o2) {
        Objects.requireNonNull(o, "key cannot be null");
        if (o2 == null) {
            if (!this.data.isEmpty()) {
                this.data.remove(o);
                if (this.data.isEmpty()) {
                    this.data = Collections.emptyMap();
                }
            }
        }
        else {
            if (this.data.isEmpty()) {
                this.data = new HashMap<Object, Object>(1, 2.0f);
            }
            this.data.put(o, o2);
        }
    }
    
    @Override
    public boolean accept(final DependencyVisitor dependencyVisitor) {
        if (dependencyVisitor.visitEnter(this)) {
            final Iterator<DependencyNode> iterator = this.children.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().accept(dependencyVisitor)) {
                    break;
                }
            }
        }
        return dependencyVisitor.visitLeave(this);
    }
    
    @Override
    public String toString() {
        final Dependency dependency = this.getDependency();
        if (dependency == null) {
            return String.valueOf(this.getArtifact());
        }
        return dependency.toString();
    }
}
