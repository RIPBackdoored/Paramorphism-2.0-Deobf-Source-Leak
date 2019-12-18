package org.eclipse.aether.internal.impl;

import org.eclipse.aether.RepositoryEvent$EventType;

class DefaultRepositoryEventDispatcher$1 {
   static final int[] $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType = new int[RepositoryEvent$EventType.values().length];

   static {
      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_DEPLOYED.ordinal()] = 1;
      } catch (NoSuchFieldError var19) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_DEPLOYING.ordinal()] = 2;
      } catch (NoSuchFieldError var18) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_DESCRIPTOR_INVALID.ordinal()] = 3;
      } catch (NoSuchFieldError var17) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_DESCRIPTOR_MISSING.ordinal()] = 4;
      } catch (NoSuchFieldError var16) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_DOWNLOADED.ordinal()] = 5;
      } catch (NoSuchFieldError var15) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_DOWNLOADING.ordinal()] = 6;
      } catch (NoSuchFieldError var14) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_INSTALLED.ordinal()] = 7;
      } catch (NoSuchFieldError var13) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_INSTALLING.ordinal()] = 8;
      } catch (NoSuchFieldError var12) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_RESOLVED.ordinal()] = 9;
      } catch (NoSuchFieldError var11) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.ARTIFACT_RESOLVING.ordinal()] = 10;
      } catch (NoSuchFieldError var10) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_DEPLOYED.ordinal()] = 11;
      } catch (NoSuchFieldError var9) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_DEPLOYING.ordinal()] = 12;
      } catch (NoSuchFieldError var8) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_DOWNLOADED.ordinal()] = 13;
      } catch (NoSuchFieldError var7) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_DOWNLOADING.ordinal()] = 14;
      } catch (NoSuchFieldError var6) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_INSTALLED.ordinal()] = 15;
      } catch (NoSuchFieldError var5) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_INSTALLING.ordinal()] = 16;
      } catch (NoSuchFieldError var4) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_INVALID.ordinal()] = 17;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_RESOLVED.ordinal()] = 18;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$eclipse$aether$RepositoryEvent$EventType[RepositoryEvent$EventType.METADATA_RESOLVING.ordinal()] = 19;
      } catch (NoSuchFieldError var1) {
      }

   }
}
