package org.eclipse.aether.transfer;

import java.io.*;
import org.eclipse.aether.*;

public final class TransferResource
{
    private final String repositoryId;
    private final String repositoryUrl;
    private final String resourceName;
    private final File file;
    private final long startTime;
    private final RequestTrace trace;
    private long contentLength;
    private long resumeOffset;
    
    @Deprecated
    public TransferResource(final String s, final String s2, final File file, final RequestTrace requestTrace) {
        this(null, s, s2, file, requestTrace);
    }
    
    public TransferResource(final String repositoryId, final String repositoryUrl, final String resourceName, final File file, final RequestTrace trace) {
        super();
        this.contentLength = -1L;
        if (repositoryId == null || repositoryId.length() <= 0) {
            this.repositoryId = "";
        }
        else {
            this.repositoryId = repositoryId;
        }
        if (repositoryUrl == null || repositoryUrl.length() <= 0) {
            this.repositoryUrl = "";
        }
        else if (repositoryUrl.endsWith("/")) {
            this.repositoryUrl = repositoryUrl;
        }
        else {
            this.repositoryUrl = repositoryUrl + '/';
        }
        if (resourceName == null || resourceName.length() <= 0) {
            this.resourceName = "";
        }
        else if (resourceName.startsWith("/")) {
            this.resourceName = resourceName.substring(1);
        }
        else {
            this.resourceName = resourceName;
        }
        this.file = file;
        this.trace = trace;
        this.startTime = System.currentTimeMillis();
    }
    
    public String getRepositoryId() {
        return this.repositoryId;
    }
    
    public String getRepositoryUrl() {
        return this.repositoryUrl;
    }
    
    public String getResourceName() {
        return this.resourceName;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public long getContentLength() {
        return this.contentLength;
    }
    
    public TransferResource setContentLength(final long contentLength) {
        this.contentLength = contentLength;
        return this;
    }
    
    public long getResumeOffset() {
        return this.resumeOffset;
    }
    
    public TransferResource setResumeOffset(final long resumeOffset) {
        if (resumeOffset < 0L) {
            throw new IllegalArgumentException("resume offset cannot be negative");
        }
        this.resumeOffset = resumeOffset;
        return this;
    }
    
    public long getTransferStartTime() {
        return this.startTime;
    }
    
    public RequestTrace getTrace() {
        return this.trace;
    }
    
    @Override
    public String toString() {
        return this.getRepositoryUrl() + this.getResourceName() + " <> " + this.getFile();
    }
}
