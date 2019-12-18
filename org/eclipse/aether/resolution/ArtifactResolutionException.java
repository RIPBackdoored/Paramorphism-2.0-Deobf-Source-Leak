package org.eclipse.aether.resolution;

import org.eclipse.aether.*;
import java.util.*;
import org.eclipse.aether.transfer.*;

public class ArtifactResolutionException extends RepositoryException
{
    private final transient List<ArtifactResult> results;
    
    public ArtifactResolutionException(final List<ArtifactResult> list) {
        super(getMessage(list), getCause(list));
        this.results = ((list != null) ? list : Collections.emptyList());
    }
    
    public ArtifactResolutionException(final List<ArtifactResult> list, final String s) {
        super(s, getCause(list));
        this.results = ((list != null) ? list : Collections.emptyList());
    }
    
    public ArtifactResolutionException(final List<ArtifactResult> list, final String s, final Throwable t) {
        super(s, t);
        this.results = ((list != null) ? list : Collections.emptyList());
    }
    
    public List<ArtifactResult> getResults() {
        return this.results;
    }
    
    public ArtifactResult getResult() {
        return (this.results != null && !this.results.isEmpty()) ? this.results.get(0) : null;
    }
    
    private static String getMessage(final List<? extends ArtifactResult> list) {
        final StringBuilder sb = new StringBuilder(256);
        sb.append("The following artifacts could not be resolved: ");
        int n = 0;
        String s = "";
        for (final ArtifactResult artifactResult : list) {
            if (!artifactResult.isResolved()) {
                ++n;
                sb.append(s);
                sb.append(artifactResult.getRequest().getArtifact());
                s = ", ";
            }
        }
        final Throwable cause = getCause(list);
        if (cause != null) {
            if (n == 1) {
                sb.setLength(0);
                sb.append(cause.getMessage());
            }
            else {
                sb.append(": ").append(cause.getMessage());
            }
        }
        return sb.toString();
    }
    
    private static Throwable getCause(final List<? extends ArtifactResult> list) {
        for (final ArtifactResult artifactResult : list) {
            if (!artifactResult.isResolved()) {
                Throwable t = null;
                Throwable t2 = null;
                for (final Exception ex : artifactResult.getExceptions()) {
                    if (!(ex instanceof ArtifactNotFoundException)) {
                        return ex;
                    }
                    if (t == null) {
                        t = ex;
                    }
                    if (t2 != null || !(ex.getCause() instanceof RepositoryOfflineException)) {
                        continue;
                    }
                    t2 = ex;
                }
                if (t2 != null) {
                    return t2;
                }
                if (t != null) {
                    return t;
                }
                continue;
            }
        }
        return null;
    }
}
