package org.slf4j.spi;

import org.slf4j.*;

public interface LoggerFactoryBinder
{
    ILoggerFactory getLoggerFactory();
    
    String getLoggerFactoryClassStr();
}
