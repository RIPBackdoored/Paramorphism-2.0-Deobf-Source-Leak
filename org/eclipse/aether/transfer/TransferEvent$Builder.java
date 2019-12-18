package org.eclipse.aether.transfer;

import java.nio.ByteBuffer;
import java.util.Objects;
import org.eclipse.aether.RepositorySystemSession;

public final class TransferEvent$Builder {
   TransferEvent$EventType type;
   TransferEvent$RequestType requestType;
   RepositorySystemSession session;
   TransferResource resource;
   ByteBuffer dataBuffer;
   long transferredBytes;
   Exception exception;

   public TransferEvent$Builder(RepositorySystemSession var1, TransferResource var2) {
      super();
      this.session = (RepositorySystemSession)Objects.requireNonNull(var1, "repository system session cannot be null");
      this.resource = (TransferResource)Objects.requireNonNull(var2, "transfer resource cannot be null");
      this.type = TransferEvent$EventType.INITIATED;
      this.requestType = TransferEvent$RequestType.GET;
   }

   private TransferEvent$Builder(TransferEvent$Builder var1) {
      super();
      this.session = var1.session;
      this.resource = var1.resource;
      this.type = var1.type;
      this.requestType = var1.requestType;
      this.dataBuffer = var1.dataBuffer;
      this.transferredBytes = var1.transferredBytes;
      this.exception = var1.exception;
   }

   public TransferEvent$Builder copy() {
      return new TransferEvent$Builder(this);
   }

   public TransferEvent$Builder resetType(TransferEvent$EventType var1) {
      // $FF: Couldn't be decompiled
   }

   public TransferEvent$Builder setType(TransferEvent$EventType var1) {
      this.type = (TransferEvent$EventType)Objects.requireNonNull(var1, "event type cannot be null");
      return this;
   }

   public TransferEvent$Builder setRequestType(TransferEvent$RequestType var1) {
      this.requestType = (TransferEvent$RequestType)Objects.requireNonNull(var1, "request type cannot be null");
      return this;
   }

   public TransferEvent$Builder setTransferredBytes(long var1) {
      if (var1 < 0L) {
         throw new IllegalArgumentException("number of transferred bytes cannot be negative");
      } else {
         this.transferredBytes = var1;
         return this;
      }
   }

   public TransferEvent$Builder addTransferredBytes(long var1) {
      if (var1 < 0L) {
         throw new IllegalArgumentException("number of transferred bytes cannot be negative");
      } else {
         this.transferredBytes += var1;
         return this;
      }
   }

   public TransferEvent$Builder setDataBuffer(byte[] var1, int var2, int var3) {
      return this.setDataBuffer(var1 != null ? ByteBuffer.wrap(var1, var2, var3) : null);
   }

   public TransferEvent$Builder setDataBuffer(ByteBuffer var1) {
      this.dataBuffer = var1;
      return this;
   }

   public TransferEvent$Builder setException(Exception var1) {
      this.exception = var1;
      return this;
   }

   public TransferEvent build() {
      return new TransferEvent(this);
   }
}
