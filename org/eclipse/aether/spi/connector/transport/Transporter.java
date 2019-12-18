package org.eclipse.aether.spi.connector.transport;

import java.io.Closeable;

public interface Transporter extends Closeable {
   int ERROR_OTHER = 0;
   int ERROR_NOT_FOUND = 1;

   int classify(Throwable var1);

   void peek(PeekTask var1) throws Exception;

   void get(GetTask var1) throws Exception;

   void put(PutTask var1) throws Exception;

   void close();
}
