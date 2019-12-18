package org.eclipse.aether.version;

public interface VersionConstraint
{
    VersionRange getRange();
    
    Version getVersion();
    
    boolean containsVersion(final Version p0);
}
