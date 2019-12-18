package org.eclipse.aether.spi.log;

public final class NullLoggerFactory implements LoggerFactory
{
    public static final LoggerFactory INSTANCE;
    public static final Logger LOGGER;
    
    @Override
    public Logger getLogger(final String s) {
        return NullLoggerFactory.LOGGER;
    }
    
    private NullLoggerFactory() {
        super();
    }
    
    public static Logger getSafeLogger(final LoggerFactory loggerFactory, final Class<?> clazz) {
        if (loggerFactory == null) {
            return NullLoggerFactory.LOGGER;
        }
        final Logger logger = loggerFactory.getLogger(clazz.getName());
        if (logger == null) {
            return NullLoggerFactory.LOGGER;
        }
        return logger;
    }
    
    static {
        INSTANCE = new NullLoggerFactory();
        LOGGER = new NullLogger();
    }
}
