package org.eclipse.aether;

import java.io.Closeable;
import java.util.Collection;

public interface SyncContext extends Closeable {
   void acquire(Collection var1, Collection var2);

   void close();
}
