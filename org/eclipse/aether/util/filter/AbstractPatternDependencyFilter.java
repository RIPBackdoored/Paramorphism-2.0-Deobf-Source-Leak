package org.eclipse.aether.util.filter;

import org.eclipse.aether.graph.*;
import org.eclipse.aether.artifact.*;
import org.eclipse.aether.version.*;
import java.util.*;

class AbstractPatternDependencyFilter implements DependencyFilter
{
    private final Set<String> patterns;
    private final VersionScheme versionScheme;
    
    AbstractPatternDependencyFilter(final String... array) {
        this((VersionScheme)null, array);
    }
    
    AbstractPatternDependencyFilter(final VersionScheme versionScheme, final String... array) {
        this(versionScheme, (array == null) ? null : Arrays.asList(array));
    }
    
    AbstractPatternDependencyFilter(final Collection<String> collection) {
        this(null, collection);
    }
    
    AbstractPatternDependencyFilter(final VersionScheme versionScheme, final Collection<String> collection) {
        super();
        this.patterns = new HashSet<String>();
        if (collection != null) {
            this.patterns.addAll(collection);
        }
        this.versionScheme = versionScheme;
    }
    
    @Override
    public boolean accept(final DependencyNode dependencyNode, final List<DependencyNode> list) {
        final Dependency dependency = dependencyNode.getDependency();
        return dependency == null || this.accept(dependency.getArtifact());
    }
    
    protected boolean accept(final Artifact artifact) {
        final Iterator<String> iterator = this.patterns.iterator();
        while (iterator.hasNext()) {
            if (this.accept(artifact, iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean accept(final Artifact artifact, final String s) {
        final String[] array = { artifact.getGroupId(), artifact.getArtifactId(), artifact.getExtension(), artifact.getBaseVersion() };
        final String[] split = s.split(":");
        boolean matches = split.length <= array.length;
        for (int n = 0; matches && n < split.length; matches = this.matches(array[n], split[n]), ++n) {}
        return matches;
    }
    
    private boolean matches(final String s, final String s2) {
        boolean b;
        if ("*".equals(s2) || s2.length() == 0) {
            b = true;
        }
        else if (s2.startsWith("*") && s2.endsWith("*")) {
            b = s.contains(s2.substring(1, s2.length() - 1));
        }
        else if (s2.startsWith("*")) {
            b = s.endsWith(s2.substring(1));
        }
        else if (s2.endsWith("*")) {
            b = s.startsWith(s2.substring(0, s2.length() - 1));
        }
        else if (s2.startsWith("[") || s2.startsWith("(")) {
            b = this.isVersionIncludedInRange(s, s2);
        }
        else {
            b = s.equals(s2);
        }
        return b;
    }
    
    private boolean isVersionIncludedInRange(final String s, final String s2) {
        if (this.versionScheme == null) {
            return false;
        }
        try {
            return this.versionScheme.parseVersionRange(s2).containsVersion(this.versionScheme.parseVersion(s));
        }
        catch (InvalidVersionSpecificationException ex) {
            return false;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        final AbstractPatternDependencyFilter abstractPatternDependencyFilter = (AbstractPatternDependencyFilter)o;
        return Objects.equals(this.patterns, abstractPatternDependencyFilter.patterns) && Objects.equals(this.versionScheme, abstractPatternDependencyFilter.versionScheme);
    }
    
    @Override
    public int hashCode() {
        return (17 * 31 + this.patterns.hashCode()) * 31 + ((this.versionScheme == null) ? 0 : this.versionScheme.hashCode());
    }
}
