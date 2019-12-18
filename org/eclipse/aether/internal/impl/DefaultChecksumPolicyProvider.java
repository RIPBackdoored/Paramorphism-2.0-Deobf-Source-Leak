package org.eclipse.aether.internal.impl;

import javax.inject.*;
import org.eclipse.aether.*;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.transfer.*;
import org.eclipse.aether.spi.connector.checksum.*;

@Named
public final class DefaultChecksumPolicyProvider implements ChecksumPolicyProvider
{
    private static final int ORDINAL_IGNORE = 0;
    private static final int ORDINAL_WARN = 1;
    private static final int ORDINAL_FAIL = 2;
    
    public DefaultChecksumPolicyProvider() {
        super();
    }
    
    @Override
    public ChecksumPolicy newChecksumPolicy(final RepositorySystemSession repositorySystemSession, final RemoteRepository remoteRepository, final TransferResource transferResource, final String s) {
        if ("ignore".equals(s)) {
            return null;
        }
        if ("fail".equals(s)) {
            return new FailChecksumPolicy(transferResource);
        }
        return new WarnChecksumPolicy(transferResource);
    }
    
    @Override
    public String getEffectiveChecksumPolicy(final RepositorySystemSession repositorySystemSession, final String s, final String s2) {
        if (s != null && s.equals(s2)) {
            return s;
        }
        final int ordinalOfPolicy = ordinalOfPolicy(s);
        final int ordinalOfPolicy2 = ordinalOfPolicy(s2);
        if (ordinalOfPolicy2 < ordinalOfPolicy) {
            return (ordinalOfPolicy2 != 1) ? s2 : "warn";
        }
        return (ordinalOfPolicy != 1) ? s : "warn";
    }
    
    private static int ordinalOfPolicy(final String s) {
        if ("fail".equals(s)) {
            return 2;
        }
        if ("ignore".equals(s)) {
            return 0;
        }
        return 1;
    }
}
