package org.eclipse.aether.impl;

import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;

public interface VersionRangeResolver {
   VersionRangeResult resolveVersionRange(RepositorySystemSession var1, VersionRangeRequest var2) throws VersionRangeResolutionException;
}
