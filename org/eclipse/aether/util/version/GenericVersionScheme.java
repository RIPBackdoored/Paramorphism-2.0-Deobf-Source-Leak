package org.eclipse.aether.util.version;

import org.eclipse.aether.version.*;
import java.util.*;

public final class GenericVersionScheme implements VersionScheme
{
    public GenericVersionScheme() {
        super();
    }
    
    @Override
    public Version parseVersion(final String s) throws InvalidVersionSpecificationException {
        return new GenericVersion(s);
    }
    
    @Override
    public VersionRange parseVersionRange(final String s) throws InvalidVersionSpecificationException {
        return new GenericVersionRange(s);
    }
    
    @Override
    public VersionConstraint parseVersionConstraint(final String s) throws InvalidVersionSpecificationException {
        final ArrayList<VersionRange> list = new ArrayList<VersionRange>();
        String s2 = s;
        while (s2.startsWith("[") || s2.startsWith("(")) {
            final int index = s2.indexOf(41);
            final int index2 = s2.indexOf(93);
            int n;
            if ((n = index2) < 0 || (index >= 0 && index < index2)) {
                n = index;
            }
            if (n < 0) {
                throw new InvalidVersionSpecificationException(s, "Unbounded version range " + s);
            }
            list.add(this.parseVersionRange(s2.substring(0, n + 1)));
            s2 = s2.substring(n + 1).trim();
            if (s2.length() <= 0 || !s2.startsWith(",")) {
                continue;
            }
            s2 = s2.substring(1).trim();
        }
        if (s2.length() > 0 && !list.isEmpty()) {
            throw new InvalidVersionSpecificationException(s, "Invalid version range " + s + ", expected [ or ( but got " + s2);
        }
        GenericVersionConstraint genericVersionConstraint;
        if (list.isEmpty()) {
            genericVersionConstraint = new GenericVersionConstraint(this.parseVersion(s));
        }
        else {
            genericVersionConstraint = new GenericVersionConstraint(UnionVersionRange.from(list));
        }
        return genericVersionConstraint;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass().equals(o.getClass()));
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
