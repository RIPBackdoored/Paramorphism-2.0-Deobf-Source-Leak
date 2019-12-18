package org.eclipse.aether.resolution;

import org.eclipse.aether.collection.*;
import org.eclipse.aether.graph.*;
import org.eclipse.aether.*;

public final class DependencyRequest
{
    private DependencyNode root;
    private CollectRequest collectRequest;
    private DependencyFilter filter;
    private RequestTrace trace;
    
    public DependencyRequest() {
        super();
    }
    
    public DependencyRequest(final DependencyNode root, final DependencyFilter filter) {
        super();
        this.setRoot(root);
        this.setFilter(filter);
    }
    
    public DependencyRequest(final CollectRequest collectRequest, final DependencyFilter filter) {
        super();
        this.setCollectRequest(collectRequest);
        this.setFilter(filter);
    }
    
    public DependencyNode getRoot() {
        return this.root;
    }
    
    public DependencyRequest setRoot(final DependencyNode root) {
        this.root = root;
        return this;
    }
    
    public CollectRequest getCollectRequest() {
        return this.collectRequest;
    }
    
    public DependencyRequest setCollectRequest(final CollectRequest collectRequest) {
        this.collectRequest = collectRequest;
        return this;
    }
    
    public DependencyFilter getFilter() {
        return this.filter;
    }
    
    public DependencyRequest setFilter(final DependencyFilter filter) {
        this.filter = filter;
        return this;
    }
    
    public RequestTrace getTrace() {
        return this.trace;
    }
    
    public DependencyRequest setTrace(final RequestTrace trace) {
        this.trace = trace;
        return this;
    }
    
    @Override
    public String toString() {
        if (this.root != null) {
            return String.valueOf(this.root);
        }
        return String.valueOf(this.collectRequest);
    }
}
