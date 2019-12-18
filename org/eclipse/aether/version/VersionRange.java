package org.eclipse.aether.version;

public interface VersionRange {
   boolean containsVersion(Version var1);

   VersionRange$Bound getLowerBound();

   VersionRange$Bound getUpperBound();
}
