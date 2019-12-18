package org.eclipse.aether.util.version;

import org.eclipse.aether.version.*;
import java.util.*;

final class GenericVersionConstraint implements VersionConstraint
{
    private final VersionRange range;
    private final Version version;
    
    GenericVersionConstraint(final VersionRange versionRange) {
        super();
        this.range = Objects.requireNonNull(versionRange, "version range cannot be null");
        this.version = null;
    }
    
    GenericVersionConstraint(final Version version) {
        super();
        this.version = Objects.requireNonNull(version, "version cannot be null");
        this.range = null;
    }
    
    @Override
    public VersionRange getRange() {
        return this.range;
    }
    
    @Override
    public Version getVersion() {
        return this.version;
    }
    
    @Override
    public boolean containsVersion(final Version version) {
        if (this.range == null) {
            return version.equals(this.version);
        }
        return this.range.containsVersion(version);
    }
    
    @Override
    public String toString() {
        return String.valueOf((this.range == null) ? this.version : this.range);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }
        final GenericVersionConstraint genericVersionConstraint = (GenericVersionConstraint)o;
        return Objects.equals(this.range, genericVersionConstraint.range) && Objects.equals(this.version, genericVersionConstraint.getVersion());
    }
    
    @Override
    public int hashCode() {
        return (17 * 31 + hash(this.getRange())) * 31 + hash(this.getVersion());
    }
    
    private static int hash(final Object o) {
        return (o != null) ? o.hashCode() : 0;
    }
}
