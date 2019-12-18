package org.eclipse.aether.util.artifact;

import org.eclipse.aether.artifact.*;
import java.io.*;
import java.util.*;

public final class SubArtifact extends AbstractArtifact
{
    private final Artifact mainArtifact;
    private final String classifier;
    private final String extension;
    private final File file;
    private final Map<String, String> properties;
    
    public SubArtifact(final Artifact artifact, final String s, final String s2) {
        this(artifact, s, s2, (File)null);
    }
    
    public SubArtifact(final Artifact artifact, final String s, final String s2, final File file) {
        this(artifact, s, s2, null, file);
    }
    
    public SubArtifact(final Artifact artifact, final String s, final String s2, final Map<String, String> map) {
        this(artifact, s, s2, map, null);
    }
    
    public SubArtifact(final Artifact artifact, final String classifier, final String extension, final Map<String, String> map, final File file) {
        super();
        this.mainArtifact = Objects.requireNonNull(artifact, "main artifact cannot be null");
        this.classifier = classifier;
        this.extension = extension;
        this.file = file;
        this.properties = AbstractArtifact.copyProperties(map);
    }
    
    private SubArtifact(final Artifact mainArtifact, final String classifier, final String extension, final File file, final Map<String, String> properties) {
        super();
        this.mainArtifact = mainArtifact;
        this.classifier = classifier;
        this.extension = extension;
        this.file = file;
        this.properties = properties;
    }
    
    @Override
    public String getGroupId() {
        return this.mainArtifact.getGroupId();
    }
    
    @Override
    public String getArtifactId() {
        return this.mainArtifact.getArtifactId();
    }
    
    @Override
    public String getVersion() {
        return this.mainArtifact.getVersion();
    }
    
    @Override
    public String getBaseVersion() {
        return this.mainArtifact.getBaseVersion();
    }
    
    @Override
    public boolean isSnapshot() {
        return this.mainArtifact.isSnapshot();
    }
    
    @Override
    public String getClassifier() {
        return expand(this.classifier, this.mainArtifact.getClassifier());
    }
    
    @Override
    public String getExtension() {
        return expand(this.extension, this.mainArtifact.getExtension());
    }
    
    @Override
    public File getFile() {
        return this.file;
    }
    
    @Override
    public Artifact setFile(final File file) {
        if (Objects.equals(this.file, file)) {
            return this;
        }
        return new SubArtifact(this.mainArtifact, this.classifier, this.extension, file, this.properties);
    }
    
    @Override
    public Map<String, String> getProperties() {
        return this.properties;
    }
    
    @Override
    public Artifact setProperties(final Map<String, String> map) {
        if (this.properties.equals(map) || (map == null && this.properties.isEmpty())) {
            return this;
        }
        return new SubArtifact(this.mainArtifact, this.classifier, this.extension, map, this.file);
    }
    
    private static String expand(final String s, final String s2) {
        String s3 = "";
        if (s != null) {
            s3 = s.replace("*", s2);
            if (s2.length() <= 0) {
                if (s.startsWith("*")) {
                    int i;
                    for (i = 0; i < s3.length(); ++i) {
                        final char char1 = s3.charAt(i);
                        if (char1 != '-' && char1 != '.') {
                            break;
                        }
                    }
                    s3 = s3.substring(i);
                }
                if (s.endsWith("*")) {
                    int j;
                    for (j = s3.length() - 1; j >= 0; --j) {
                        final char char2 = s3.charAt(j);
                        if (char2 != '-' && char2 != '.') {
                            break;
                        }
                    }
                    s3 = s3.substring(0, j + 1);
                }
            }
        }
        return s3;
    }
}
