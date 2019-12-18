package org.jline.builtins;

import java.io.*;

static class InterruptibleInputStream extends FilterInputStream
{
    InterruptibleInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException();
        }
        return super.read(array, n, n2);
    }
}
