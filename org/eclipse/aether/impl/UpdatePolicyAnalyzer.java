package org.eclipse.aether.impl;

import org.eclipse.aether.*;

public interface UpdatePolicyAnalyzer
{
    String getEffectiveUpdatePolicy(final RepositorySystemSession p0, final String p1, final String p2);
    
    boolean isUpdatedRequired(final RepositorySystemSession p0, final long p1, final String p2);
}
