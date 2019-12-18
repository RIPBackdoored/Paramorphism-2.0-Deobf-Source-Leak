package org.eclipse.aether.spi.connector.layout;

import org.eclipse.aether.artifact.*;
import java.net.*;
import org.eclipse.aether.metadata.*;
import java.util.*;

public interface RepositoryLayout
{
    URI getLocation(final Artifact p0, final boolean p1);
    
    URI getLocation(final Metadata p0, final boolean p1);
    
    List<Checksum> getChecksums(final Artifact p0, final boolean p1, final URI p2);
    
    List<Checksum> getChecksums(final Metadata p0, final boolean p1, final URI p2);
    
    public static final class Checksum
    {
        private final String algorithm;
        private final URI location;
        
        public Checksum(final String algorithm, final URI location) {
            super();
            verify(algorithm, location);
            this.algorithm = algorithm;
            this.location = location;
        }
        
        public static Checksum forLocation(final URI uri, final String s) {
            verify(s, uri);
            if (uri.getRawQuery() != null) {
                throw new IllegalArgumentException("resource location must not have query parameters: " + uri);
            }
            if (uri.getRawFragment() != null) {
                throw new IllegalArgumentException("resource location must not have a fragment: " + uri);
            }
            return new Checksum(s, URI.create(uri.toString() + ('.' + s.replace("-", "").toLowerCase(Locale.ENGLISH))));
        }
        
        private static void verify(final String s, final URI uri) {
            Objects.requireNonNull(s, "checksum algorithm cannot be null");
            if (s.length() == 0) {
                throw new IllegalArgumentException("checksum algorithm cannot be empty");
            }
            Objects.requireNonNull(uri, "checksum location cannot be null");
            if (uri.isAbsolute()) {
                throw new IllegalArgumentException("checksum location must be relative");
            }
        }
        
        public String getAlgorithm() {
            return this.algorithm;
        }
        
        public URI getLocation() {
            return this.location;
        }
        
        @Override
        public String toString() {
            return this.location + " (" + this.algorithm + ")";
        }
    }
}
