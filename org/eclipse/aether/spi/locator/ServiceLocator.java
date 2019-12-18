package org.eclipse.aether.spi.locator;

import java.util.*;

public interface ServiceLocator
{
     <T> T getService(final Class<T> p0);
    
     <T> List<T> getServices(final Class<T> p0);
}
