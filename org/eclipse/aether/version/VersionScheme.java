package org.eclipse.aether.version;

public interface VersionScheme
{
    Version parseVersion(final String p0) throws InvalidVersionSpecificationException;
    
    VersionRange parseVersionRange(final String p0) throws InvalidVersionSpecificationException;
    
    VersionConstraint parseVersionConstraint(final String p0) throws InvalidVersionSpecificationException;
}
