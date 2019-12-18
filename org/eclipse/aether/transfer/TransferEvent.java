package org.eclipse.aether.transfer;

import java.nio.ByteBuffer;
import org.eclipse.aether.RepositorySystemSession;

public final class TransferEvent {
   private final TransferEvent$EventType type;
   private final TransferEvent$RequestType requestType;
   private final RepositorySystemSession session;
   private final TransferResource resource;
   private final ByteBuffer dataBuffer;
   private final long transferredBytes;
   private final Exception exception;

   TransferEvent(TransferEvent$Builder var1) {
      super();
      this.type = var1.type;
      this.requestType = var1.requestType;
      this.session = var1.session;
      this.resource = var1.resource;
      this.dataBuffer = var1.dataBuffer;
      this.transferredBytes = var1.transferredBytes;
      this.exception = var1.exception;
   }

   public TransferEvent$EventType getType() {
      return this.type;
   }

   public TransferEvent$RequestType getRequestType() {
      return this.requestType;
   }

   public RepositorySystemSession getSession() {
      return this.session;
   }

   public TransferResource getResource() {
      return this.resource;
   }

   public long getTransferredBytes() {
      return this.transferredBytes;
   }

   public ByteBuffer getDataBuffer() {
      return this.dataBuffer != null ? this.dataBuffer.asReadOnlyBuffer() : null;
   }

   public int getDataLength() {
      return this.dataBuffer != null ? this.dataBuffer.remaining() : 0;
   }

   public Exception getException() {
      return this.exception;
   }

   public String toString() {
      return this.getRequestType() + " " + this.getType() + " " + this.getResource();
   }
}
