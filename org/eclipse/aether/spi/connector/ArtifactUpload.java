package org.eclipse.aether.spi.connector;

import org.eclipse.aether.transform.*;
import org.eclipse.aether.artifact.*;
import java.io.*;
import org.eclipse.aether.transfer.*;
import org.eclipse.aether.*;

public final class ArtifactUpload extends ArtifactTransfer
{
    private FileTransformer fileTransformer;
    
    public ArtifactUpload() {
        super();
    }
    
    public ArtifactUpload(final Artifact artifact, final File file) {
        super();
        this.setArtifact(artifact);
        this.setFile(file);
    }
    
    public ArtifactUpload(final Artifact artifact, final File file, final FileTransformer fileTransformer) {
        super();
        this.setArtifact(artifact);
        this.setFile(file);
        this.setFileTransformer(fileTransformer);
    }
    
    @Override
    public ArtifactUpload setArtifact(final Artifact artifact) {
        super.setArtifact(artifact);
        return this;
    }
    
    @Override
    public ArtifactUpload setFile(final File file) {
        super.setFile(file);
        return this;
    }
    
    @Override
    public ArtifactUpload setException(final ArtifactTransferException exception) {
        super.setException(exception);
        return this;
    }
    
    public ArtifactUpload setListener(final TransferListener listener) {
        super.setListener(listener);
        return this;
    }
    
    public ArtifactUpload setTrace(final RequestTrace trace) {
        super.setTrace(trace);
        return this;
    }
    
    public ArtifactUpload setFileTransformer(final FileTransformer fileTransformer) {
        this.fileTransformer = fileTransformer;
        return this;
    }
    
    public FileTransformer getFileTransformer() {
        return this.fileTransformer;
    }
    
    @Override
    public String toString() {
        if (this.getFileTransformer() != null) {
            return this.getArtifact() + " >>> " + this.getFileTransformer().transformArtifact(this.getArtifact()) + " - " + this.getFile();
        }
        return this.getArtifact() + " - " + this.getFile();
    }
    
    @Override
    public ArtifactTransfer setException(final ArtifactTransferException exception) {
        return this.setException(exception);
    }
    
    @Override
    public ArtifactTransfer setFile(final File file) {
        return this.setFile(file);
    }
    
    @Override
    public ArtifactTransfer setArtifact(final Artifact artifact) {
        return this.setArtifact(artifact);
    }
    
    public Transfer setTrace(final RequestTrace trace) {
        return this.setTrace(trace);
    }
    
    public Transfer setListener(final TransferListener listener) {
        return this.setListener(listener);
    }
}
