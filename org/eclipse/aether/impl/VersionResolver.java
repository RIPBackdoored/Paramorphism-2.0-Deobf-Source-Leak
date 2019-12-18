package org.eclipse.aether.impl;

import org.eclipse.aether.*;
import org.eclipse.aether.resolution.*;

public interface VersionResolver
{
    VersionResult resolveVersion(final RepositorySystemSession p0, final VersionRequest p1) throws VersionResolutionException;
}
