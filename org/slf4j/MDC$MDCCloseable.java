package org.slf4j;

import java.io.*;

public static class MDCCloseable implements Closeable
{
    private final String key;
    
    private MDCCloseable(final String key) {
        super();
        this.key = key;
    }
    
    public void close() {
        MDC.remove(this.key);
    }
    
    MDCCloseable(final String x0, final MDC$1 x1) {
        this(x0);
    }
}
