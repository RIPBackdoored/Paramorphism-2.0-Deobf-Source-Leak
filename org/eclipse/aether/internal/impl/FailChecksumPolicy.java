package org.eclipse.aether.internal.impl;

import org.eclipse.aether.transfer.*;

final class FailChecksumPolicy extends AbstractChecksumPolicy
{
    FailChecksumPolicy(final TransferResource transferResource) {
        super(transferResource);
    }
    
    @Override
    public boolean onTransferChecksumFailure(final ChecksumFailureException ex) {
        return false;
    }
}
