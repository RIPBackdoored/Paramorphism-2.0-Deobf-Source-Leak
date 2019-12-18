package org.eclipse.aether.artifact;

import java.util.regex.*;
import java.io.*;
import java.util.*;

public abstract class AbstractArtifact implements Artifact
{
    private static final String SNAPSHOT = "SNAPSHOT";
    private static final Pattern SNAPSHOT_TIMESTAMP;
    
    public AbstractArtifact() {
        super();
    }
    
    @Override
    public boolean isSnapshot() {
        return isSnapshot(this.getVersion());
    }
    
    private static boolean isSnapshot(final String s) {
        return s.endsWith("SNAPSHOT") || AbstractArtifact.SNAPSHOT_TIMESTAMP.matcher(s).matches();
    }
    
    @Override
    public String getBaseVersion() {
        return toBaseVersion(this.getVersion());
    }
    
    private static String toBaseVersion(final String s) {
        String string;
        if (s == null) {
            string = s;
        }
        else if (s.startsWith("[") || s.startsWith("(")) {
            string = s;
        }
        else {
            final Matcher matcher = AbstractArtifact.SNAPSHOT_TIMESTAMP.matcher(s);
            if (matcher.matches()) {
                if (matcher.group(1) != null) {
                    string = matcher.group(1) + "SNAPSHOT";
                }
                else {
                    string = "SNAPSHOT";
                }
            }
            else {
                string = s;
            }
        }
        return string;
    }
    
    private Artifact newInstance(final String s, final Map<String, String> map, final File file) {
        return new DefaultArtifact(this.getGroupId(), this.getArtifactId(), this.getClassifier(), this.getExtension(), s, file, map);
    }
    
    @Override
    public Artifact setVersion(final String s) {
        final String version = this.getVersion();
        if (version.equals(s) || (s == null && version.length() <= 0)) {
            return this;
        }
        return this.newInstance(s, this.getProperties(), this.getFile());
    }
    
    @Override
    public Artifact setFile(final File file) {
        if (Objects.equals(this.getFile(), file)) {
            return this;
        }
        return this.newInstance(this.getVersion(), this.getProperties(), file);
    }
    
    @Override
    public Artifact setProperties(final Map<String, String> map) {
        final Map<String, String> properties = this.getProperties();
        if (properties.equals(map) || (map == null && properties.isEmpty())) {
            return this;
        }
        return this.newInstance(this.getVersion(), copyProperties(map), this.getFile());
    }
    
    @Override
    public String getProperty(final String s, final String s2) {
        final String s3 = this.getProperties().get(s);
        return (s3 != null) ? s3 : s2;
    }
    
    protected static Map<String, String> copyProperties(final Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            return Collections.unmodifiableMap((Map<? extends String, ? extends String>)new HashMap<String, String>(map));
        }
        return Collections.emptyMap();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(this.getGroupId());
        sb.append(':').append(this.getArtifactId());
        sb.append(':').append(this.getExtension());
        if (this.getClassifier().length() > 0) {
            sb.append(':').append(this.getClassifier());
        }
        sb.append(':').append(this.getVersion());
        return sb.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Artifact)) {
            return false;
        }
        final Artifact artifact = (Artifact)o;
        return Objects.equals(this.getArtifactId(), artifact.getArtifactId()) && Objects.equals(this.getGroupId(), artifact.getGroupId()) && Objects.equals(this.getVersion(), artifact.getVersion()) && Objects.equals(this.getExtension(), artifact.getExtension()) && Objects.equals(this.getClassifier(), artifact.getClassifier()) && Objects.equals(this.getFile(), artifact.getFile()) && Objects.equals(this.getProperties(), artifact.getProperties());
    }
    
    @Override
    public int hashCode() {
        return (((((17 * 31 + this.getGroupId().hashCode()) * 31 + this.getArtifactId().hashCode()) * 31 + this.getExtension().hashCode()) * 31 + this.getClassifier().hashCode()) * 31 + this.getVersion().hashCode()) * 31 + hash(this.getFile());
    }
    
    private static int hash(final Object o) {
        return (o != null) ? o.hashCode() : 0;
    }
    
    static {
        SNAPSHOT_TIMESTAMP = Pattern.compile("^(.*-)?([0-9]{8}\\.[0-9]{6}-[0-9]+)$");
    }
}
