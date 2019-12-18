package org.eclipse.aether.resolution;

import org.eclipse.aether.repository.*;
import org.eclipse.aether.version.*;
import java.util.*;

public final class VersionRangeResult
{
    private final VersionRangeRequest request;
    private List<Exception> exceptions;
    private List<Version> versions;
    private Map<Version, ArtifactRepository> repositories;
    private VersionConstraint versionConstraint;
    
    public VersionRangeResult(final VersionRangeRequest versionRangeRequest) {
        super();
        this.request = Objects.requireNonNull(versionRangeRequest, "version range request cannot be null");
        this.exceptions = Collections.emptyList();
        this.versions = Collections.emptyList();
        this.repositories = Collections.emptyMap();
    }
    
    public VersionRangeRequest getRequest() {
        return this.request;
    }
    
    public List<Exception> getExceptions() {
        return this.exceptions;
    }
    
    public VersionRangeResult addException(final Exception ex) {
        if (ex != null) {
            if (this.exceptions.isEmpty()) {
                this.exceptions = new ArrayList<Exception>();
            }
            this.exceptions.add(ex);
        }
        return this;
    }
    
    public List<Version> getVersions() {
        return this.versions;
    }
    
    public VersionRangeResult addVersion(final Version version) {
        if (this.versions.isEmpty()) {
            this.versions = new ArrayList<Version>();
        }
        this.versions.add(version);
        return this;
    }
    
    public VersionRangeResult setVersions(final List<Version> versions) {
        if (versions == null) {
            this.versions = Collections.emptyList();
        }
        else {
            this.versions = versions;
        }
        return this;
    }
    
    public Version getLowestVersion() {
        if (this.versions.isEmpty()) {
            return null;
        }
        return this.versions.get(0);
    }
    
    public Version getHighestVersion() {
        if (this.versions.isEmpty()) {
            return null;
        }
        return this.versions.get(this.versions.size() - 1);
    }
    
    public ArtifactRepository getRepository(final Version version) {
        return this.repositories.get(version);
    }
    
    public VersionRangeResult setRepository(final Version version, final ArtifactRepository artifactRepository) {
        if (artifactRepository != null) {
            if (this.repositories.isEmpty()) {
                this.repositories = new HashMap<Version, ArtifactRepository>();
            }
            this.repositories.put(version, artifactRepository);
        }
        return this;
    }
    
    public VersionConstraint getVersionConstraint() {
        return this.versionConstraint;
    }
    
    public VersionRangeResult setVersionConstraint(final VersionConstraint versionConstraint) {
        this.versionConstraint = versionConstraint;
        return this;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.repositories);
    }
}
