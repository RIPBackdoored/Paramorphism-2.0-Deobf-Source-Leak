package org.eclipse.aether.installation;

import org.eclipse.aether.*;

public class InstallationException extends RepositoryException
{
    public InstallationException(final String s) {
        super(s);
    }
    
    public InstallationException(final String s, final Throwable t) {
        super(s, t);
    }
}
