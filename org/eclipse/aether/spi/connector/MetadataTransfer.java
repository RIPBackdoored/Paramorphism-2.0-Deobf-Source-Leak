package org.eclipse.aether.spi.connector;

import org.eclipse.aether.metadata.*;
import java.io.*;
import org.eclipse.aether.transfer.*;

public abstract class MetadataTransfer extends Transfer
{
    private Metadata metadata;
    private File file;
    private MetadataTransferException exception;
    
    MetadataTransfer() {
        super();
    }
    
    public Metadata getMetadata() {
        return this.metadata;
    }
    
    public MetadataTransfer setMetadata(final Metadata metadata) {
        this.metadata = metadata;
        return this;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public MetadataTransfer setFile(final File file) {
        this.file = file;
        return this;
    }
    
    @Override
    public MetadataTransferException getException() {
        return this.exception;
    }
    
    public MetadataTransfer setException(final MetadataTransferException exception) {
        this.exception = exception;
        return this;
    }
    
    @Override
    public Exception getException() {
        return this.getException();
    }
}
