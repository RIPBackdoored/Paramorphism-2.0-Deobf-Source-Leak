package org.eclipse.aether.transfer;

public final class TransferEvent$EventType extends Enum {
   public static final TransferEvent$EventType INITIATED = new TransferEvent$EventType("INITIATED", 0);
   public static final TransferEvent$EventType STARTED = new TransferEvent$EventType("STARTED", 1);
   public static final TransferEvent$EventType PROGRESSED = new TransferEvent$EventType("PROGRESSED", 2);
   public static final TransferEvent$EventType CORRUPTED = new TransferEvent$EventType("CORRUPTED", 3);
   public static final TransferEvent$EventType SUCCEEDED = new TransferEvent$EventType("SUCCEEDED", 4);
   public static final TransferEvent$EventType FAILED = new TransferEvent$EventType("FAILED", 5);
   private static final TransferEvent$EventType[] $VALUES = new TransferEvent$EventType[]{INITIATED, STARTED, PROGRESSED, CORRUPTED, SUCCEEDED, FAILED};

   public static TransferEvent$EventType[] values() {
      return (TransferEvent$EventType[])$VALUES.clone();
   }

   public static TransferEvent$EventType valueOf(String var0) {
      return (TransferEvent$EventType)Enum.valueOf(TransferEvent$EventType.class, var0);
   }

   private TransferEvent$EventType(String var1, int var2) {
      super(var1, var2);
   }
}
