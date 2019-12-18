package org.eclipse.aether.spi.connector.checksum;

import org.eclipse.aether.transfer.*;

public interface ChecksumPolicy
{
    public static final int KIND_UNOFFICIAL = 1;
    
    boolean onChecksumMatch(final String p0, final int p1);
    
    void onChecksumMismatch(final String p0, final int p1, final ChecksumFailureException p2) throws ChecksumFailureException;
    
    void onChecksumError(final String p0, final int p1, final ChecksumFailureException p2) throws ChecksumFailureException;
    
    void onNoMoreChecksums() throws ChecksumFailureException;
    
    void onTransferRetry();
    
    boolean onTransferChecksumFailure(final ChecksumFailureException p0);
}
