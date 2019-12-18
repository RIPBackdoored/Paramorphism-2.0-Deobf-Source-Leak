package org.eclipse.aether.spi.connector.checksum;

import org.eclipse.aether.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.transfer.*;

public interface ChecksumPolicyProvider
{
    ChecksumPolicy newChecksumPolicy(final RepositorySystemSession p0, final RemoteRepository p1, final TransferResource p2, final String p3);
    
    String getEffectiveChecksumPolicy(final RepositorySystemSession p0, final String p1, final String p2);
}
