package org.eclipse.aether.internal.impl.collect;

import org.eclipse.aether.version.*;
import java.util.*;
import org.eclipse.aether.resolution.*;
import org.eclipse.aether.repository.*;

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
