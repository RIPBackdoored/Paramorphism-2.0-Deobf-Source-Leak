package org.eclipse.aether.spi.connector;

import java.io.Closeable;
import java.util.Collection;

public interface RepositoryConnector extends Closeable {
   void get(Collection var1, Collection var2);

   void put(Collection var1, Collection var2);

   void close();
}
