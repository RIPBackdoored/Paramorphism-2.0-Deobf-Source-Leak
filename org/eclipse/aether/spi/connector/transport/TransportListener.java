package org.eclipse.aether.spi.connector.transport;

import java.nio.ByteBuffer;
import org.eclipse.aether.transfer.TransferCancelledException;

public abstract class TransportListener {
   protected TransportListener() {
      super();
   }

   public void transportStarted(long var1, long var3) throws TransferCancelledException {
   }

   public void transportProgressed(ByteBuffer var1) throws TransferCancelledException {
   }
}
