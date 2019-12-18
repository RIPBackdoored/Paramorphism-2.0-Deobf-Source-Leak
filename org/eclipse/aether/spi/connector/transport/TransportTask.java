package org.eclipse.aether.spi.connector.transport;

import java.net.URI;
import java.util.Objects;

public abstract class TransportTask {
   static final TransportListener NOOP = new TransportTask$1();
   static final byte[] EMPTY = new byte[0];
   private URI location;
   private TransportListener listener;

   TransportTask() {
      super();
      this.listener = NOOP;
   }

   public URI getLocation() {
      return this.location;
   }

   TransportTask setLocation(URI var1) {
      this.location = (URI)Objects.requireNonNull(var1, "location type cannot be null");
      return this;
   }

   public TransportListener getListener() {
      return this.listener;
   }

   TransportTask setListener(TransportListener var1) {
      this.listener = var1 != null ? var1 : NOOP;
      return this;
   }
}
