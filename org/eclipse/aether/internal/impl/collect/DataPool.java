package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.artifact.*;
import org.eclipse.aether.graph.*;
import org.eclipse.aether.*;
import org.eclipse.aether.resolution.*;
import org.eclipse.aether.collection.*;
import org.eclipse.aether.version.*;
import org.eclipse.aether.repository.*;
import java.util.*;

final class DataPool
{
    private static final String ARTIFACT_POOL;
    private static final String DEPENDENCY_POOL;
    private static final String DESCRIPTORS;
    static final ArtifactDescriptorResult NO_DESCRIPTOR;
    private ObjectPool<Artifact> artifacts;
    private ObjectPool<Dependency> dependencies;
    private Map<Object, Descriptor> descriptors;
    private Map<Object, Constraint> constraints;
    private Map<Object, List<DependencyNode>> nodes;
    
    DataPool(final RepositorySystemSession repositorySystemSession) {
        super();
        this.constraints = new HashMap<Object, Constraint>();
        this.nodes = new HashMap<Object, List<DependencyNode>>(256);
        final RepositoryCache cache = repositorySystemSession.getCache();
        if (cache != null) {
            this.artifacts = (ObjectPool<Artifact>)cache.get(repositorySystemSession, DataPool.ARTIFACT_POOL);
            this.dependencies = (ObjectPool<Dependency>)cache.get(repositorySystemSession, DataPool.DEPENDENCY_POOL);
            this.descriptors = (Map<Object, Descriptor>)cache.get(repositorySystemSession, DataPool.DESCRIPTORS);
        }
        if (this.artifacts == null) {
            this.artifacts = new ObjectPool<Artifact>();
            if (cache != null) {
                cache.put(repositorySystemSession, DataPool.ARTIFACT_POOL, this.artifacts);
            }
        }
        if (this.dependencies == null) {
            this.dependencies = new ObjectPool<Dependency>();
            if (cache != null) {
                cache.put(repositorySystemSession, DataPool.DEPENDENCY_POOL, this.dependencies);
            }
        }
        if (this.descriptors == null) {
            this.descriptors = Collections.synchronizedMap(new WeakHashMap<Object, Descriptor>(256));
            if (cache != null) {
                cache.put(repositorySystemSession, DataPool.DESCRIPTORS, this.descriptors);
            }
        }
    }
    
    public Artifact intern(final Artifact artifact) {
        return this.artifacts.intern(artifact);
    }
    
    public Dependency intern(final Dependency dependency) {
        return this.dependencies.intern(dependency);
    }
    
    Object toKey(final ArtifactDescriptorRequest artifactDescriptorRequest) {
        return artifactDescriptorRequest.getArtifact();
    }
    
    ArtifactDescriptorResult getDescriptor(final Object o, final ArtifactDescriptorRequest artifactDescriptorRequest) {
        final Descriptor descriptor = this.descriptors.get(o);
        if (descriptor != null) {
            return descriptor.toResult(artifactDescriptorRequest);
        }
        return null;
    }
    
    void putDescriptor(final Object o, final ArtifactDescriptorResult artifactDescriptorResult) {
        this.descriptors.put(o, new GoodDescriptor(artifactDescriptorResult));
    }
    
    void putDescriptor(final Object o, final ArtifactDescriptorException ex) {
        this.descriptors.put(o, BadDescriptor.INSTANCE);
    }
    
    Object toKey(final VersionRangeRequest versionRangeRequest) {
        return new ConstraintKey(versionRangeRequest);
    }
    
    VersionRangeResult getConstraint(final Object o, final VersionRangeRequest versionRangeRequest) {
        final Constraint constraint = this.constraints.get(o);
        if (constraint != null) {
            return constraint.toResult(versionRangeRequest);
        }
        return null;
    }
    
    void putConstraint(final Object o, final VersionRangeResult versionRangeResult) {
        this.constraints.put(o, new Constraint(versionRangeResult));
    }
    
    public Object toKey(final Artifact artifact, final List<RemoteRepository> list, final DependencySelector dependencySelector, final DependencyManager dependencyManager, final DependencyTraverser dependencyTraverser, final VersionFilter versionFilter) {
        return new GraphKey(artifact, list, dependencySelector, dependencyManager, dependencyTraverser, versionFilter);
    }
    
    public List<DependencyNode> getChildren(final Object o) {
        return this.nodes.get(o);
    }
    
    public void putChildren(final Object o, final List<DependencyNode> list) {
        this.nodes.put(o, list);
    }
    
    static {
        ARTIFACT_POOL = DataPool.class.getName() + "$Artifact";
        DEPENDENCY_POOL = DataPool.class.getName() + "$Dependency";
        DESCRIPTORS = DataPool.class.getName() + "$Descriptors";
        NO_DESCRIPTOR = new ArtifactDescriptorResult(new ArtifactDescriptorRequest());
    }
    
    abstract static class Descriptor
    {
        Descriptor() {
            super();
        }
        
        public abstract ArtifactDescriptorResult toResult(final ArtifactDescriptorRequest p0);
    }
    
    static final class GoodDescriptor extends Descriptor
    {
        final Artifact artifact;
        final List<Artifact> relocations;
        final Collection<Artifact> aliases;
        final List<RemoteRepository> repositories;
        final List<Dependency> dependencies;
        final List<Dependency> managedDependencies;
        
        GoodDescriptor(final ArtifactDescriptorResult artifactDescriptorResult) {
            super();
            this.artifact = artifactDescriptorResult.getArtifact();
            this.relocations = artifactDescriptorResult.getRelocations();
            this.aliases = artifactDescriptorResult.getAliases();
            this.dependencies = artifactDescriptorResult.getDependencies();
            this.managedDependencies = artifactDescriptorResult.getManagedDependencies();
            this.repositories = artifactDescriptorResult.getRepositories();
        }
        
        @Override
        public ArtifactDescriptorResult toResult(final ArtifactDescriptorRequest artifactDescriptorRequest) {
            final ArtifactDescriptorResult artifactDescriptorResult = new ArtifactDescriptorResult(artifactDescriptorRequest);
            artifactDescriptorResult.setArtifact(this.artifact);
            artifactDescriptorResult.setRelocations(this.relocations);
            artifactDescriptorResult.setAliases(this.aliases);
            artifactDescriptorResult.setDependencies(this.dependencies);
            artifactDescriptorResult.setManagedDependencies(this.managedDependencies);
            artifactDescriptorResult.setRepositories(this.repositories);
            return artifactDescriptorResult;
        }
    }
    
    static final class BadDescriptor extends Descriptor
    {
        static final BadDescriptor INSTANCE;
        
        BadDescriptor() {
            super();
        }
        
        @Override
        public ArtifactDescriptorResult toResult(final ArtifactDescriptorRequest artifactDescriptorRequest) {
            return DataPool.NO_DESCRIPTOR;
        }
        
        static {
            INSTANCE = new BadDescriptor();
        }
    }
    
    private static final class Constraint
    {
        final VersionRepo[] repositories;
        final VersionConstraint versionConstraint;
        
        Constraint(final VersionRangeResult versionRangeResult) {
            super();
            this.versionConstraint = versionRangeResult.getVersionConstraint();
            final List<Version> versions = versionRangeResult.getVersions();
            this.repositories = new VersionRepo[versions.size()];
            int n = 0;
            for (final Version version : versions) {
                this.repositories[n++] = new VersionRepo(version, versionRangeResult.getRepository(version));
            }
        }
        
        VersionRangeResult toResult(final VersionRangeRequest versionRangeRequest) {
            final VersionRangeResult versionRangeResult = new VersionRangeResult(versionRangeRequest);
            for (final VersionRepo versionRepo : this.repositories) {
                versionRangeResult.addVersion(versionRepo.version);
                versionRangeResult.setRepository(versionRepo.version, versionRepo.repo);
            }
            versionRangeResult.setVersionConstraint(this.versionConstraint);
            return versionRangeResult;
        }
        
        static final class VersionRepo
        {
            final Version version;
            final ArtifactRepository repo;
            
            VersionRepo(final Version version, final ArtifactRepository repo) {
                super();
                this.version = version;
                this.repo = repo;
            }
        }
    }
    
    static final class ConstraintKey
    {
        private final Artifact artifact;
        private final List<RemoteRepository> repositories;
        private final int hashCode;
        
        ConstraintKey(final VersionRangeRequest versionRangeRequest) {
            super();
            this.artifact = versionRangeRequest.getArtifact();
            this.repositories = versionRangeRequest.getRepositories();
            this.hashCode = this.artifact.hashCode();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ConstraintKey)) {
                return false;
            }
            final ConstraintKey constraintKey = (ConstraintKey)o;
            return this.artifact.equals(constraintKey.artifact) && equals(this.repositories, constraintKey.repositories);
        }
        
        private static boolean equals(final List<RemoteRepository> list, final List<RemoteRepository> list2) {
            if (list.size() != list2.size()) {
                return false;
            }
            for (int i = 0; i < list.size(); ++i) {
                final RemoteRepository remoteRepository = list.get(i);
                final RemoteRepository remoteRepository2 = list2.get(i);
                if (remoteRepository.isRepositoryManager() != remoteRepository2.isRepositoryManager()) {
                    return false;
                }
                if (remoteRepository.isRepositoryManager()) {
                    if (!equals(remoteRepository.getMirroredRepositories(), remoteRepository2.getMirroredRepositories())) {
                        return false;
                    }
                }
                else {
                    if (!remoteRepository.getUrl().equals(remoteRepository2.getUrl())) {
                        return false;
                    }
                    if (remoteRepository.getPolicy(true).isEnabled() != remoteRepository2.getPolicy(true).isEnabled()) {
                        return false;
                    }
                    if (remoteRepository.getPolicy(false).isEnabled() != remoteRepository2.getPolicy(false).isEnabled()) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            return this.hashCode;
        }
    }
    
    static final class GraphKey
    {
        private final Artifact artifact;
        private final List<RemoteRepository> repositories;
        private final DependencySelector selector;
        private final DependencyManager manager;
        private final DependencyTraverser traverser;
        private final VersionFilter filter;
        private final int hashCode;
        
        GraphKey(final Artifact artifact, final List<RemoteRepository> repositories, final DependencySelector selector, final DependencyManager manager, final DependencyTraverser traverser, final VersionFilter filter) {
            super();
            this.artifact = artifact;
            this.repositories = repositories;
            this.selector = selector;
            this.manager = manager;
            this.traverser = traverser;
            this.filter = filter;
            this.hashCode = Objects.hash(artifact, repositories, selector, manager, traverser, filter);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof GraphKey)) {
                return false;
            }
            final GraphKey graphKey = (GraphKey)o;
            return Objects.equals(this.artifact, graphKey.artifact) && Objects.equals(this.repositories, graphKey.repositories) && Objects.equals(this.selector, graphKey.selector) && Objects.equals(this.manager, graphKey.manager) && Objects.equals(this.traverser, graphKey.traverser) && Objects.equals(this.filter, graphKey.filter);
        }
        
        @Override
        public int hashCode() {
            return this.hashCode;
        }
    }
}
