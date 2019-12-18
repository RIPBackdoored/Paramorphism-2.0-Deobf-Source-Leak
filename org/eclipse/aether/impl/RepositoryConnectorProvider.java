package org.eclipse.aether.impl;

import org.eclipse.aether.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.spi.connector.*;
import org.eclipse.aether.transfer.*;

public interface RepositoryConnectorProvider
{
    RepositoryConnector newRepositoryConnector(final RepositorySystemSession p0, final RemoteRepository p1) throws NoRepositoryConnectorException;
}
