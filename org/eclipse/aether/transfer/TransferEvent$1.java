package org.eclipse.aether.transfer;

class TransferEvent$1 {
   static final int[] $SwitchMap$org$eclipse$aether$transfer$TransferEvent$EventType = new int[TransferEvent$EventType.values().length];

   static {
      try {
         $SwitchMap$org$eclipse$aether$transfer$TransferEvent$EventType[TransferEvent$EventType.INITIATED.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$eclipse$aether$transfer$TransferEvent$EventType[TransferEvent$EventType.STARTED.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
      }

   }
}
