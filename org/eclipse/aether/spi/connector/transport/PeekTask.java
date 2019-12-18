package org.eclipse.aether.spi.connector.transport;

import java.net.URI;

public final class PeekTask extends TransportTask {
   public PeekTask(URI var1) {
      super();
      this.setLocation(var1);
   }

   public String toString() {
      return "?? " + this.getLocation();
   }
}
