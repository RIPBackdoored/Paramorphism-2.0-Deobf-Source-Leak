package org.eclipse.aether.spi.connector;

import org.eclipse.aether.transfer.*;
import org.eclipse.aether.*;

public abstract class Transfer
{
    private TransferListener listener;
    private RequestTrace trace;
    
    Transfer() {
        super();
    }
    
    public abstract Exception getException();
    
    public TransferListener getListener() {
        return this.listener;
    }
    
    Transfer setListener(final TransferListener listener) {
        this.listener = listener;
        return this;
    }
    
    public RequestTrace getTrace() {
        return this.trace;
    }
    
    Transfer setTrace(final RequestTrace trace) {
        this.trace = trace;
        return this;
    }
}
