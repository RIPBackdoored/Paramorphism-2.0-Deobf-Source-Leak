package org.eclipse.aether.spi.log;

final class NullLogger implements Logger
{
    NullLogger() {
        super();
    }
    
    @Override
    public boolean isDebugEnabled() {
        return false;
    }
    
    @Override
    public void debug(final String s) {
    }
    
    @Override
    public void debug(final String s, final Throwable t) {
    }
    
    @Override
    public boolean isWarnEnabled() {
        return false;
    }
    
    @Override
    public void warn(final String s) {
    }
    
    @Override
    public void warn(final String s, final Throwable t) {
    }
}
