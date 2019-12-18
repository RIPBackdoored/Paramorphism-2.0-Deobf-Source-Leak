package org.eclipse.aether;

public final class RepositoryEvent$EventType extends Enum {
   public static final RepositoryEvent$EventType ARTIFACT_DESCRIPTOR_INVALID = new RepositoryEvent$EventType("ARTIFACT_DESCRIPTOR_INVALID", 0);
   public static final RepositoryEvent$EventType ARTIFACT_DESCRIPTOR_MISSING = new RepositoryEvent$EventType("ARTIFACT_DESCRIPTOR_MISSING", 1);
   public static final RepositoryEvent$EventType METADATA_INVALID = new RepositoryEvent$EventType("METADATA_INVALID", 2);
   public static final RepositoryEvent$EventType ARTIFACT_RESOLVING = new RepositoryEvent$EventType("ARTIFACT_RESOLVING", 3);
   public static final RepositoryEvent$EventType ARTIFACT_RESOLVED = new RepositoryEvent$EventType("ARTIFACT_RESOLVED", 4);
   public static final RepositoryEvent$EventType METADATA_RESOLVING = new RepositoryEvent$EventType("METADATA_RESOLVING", 5);
   public static final RepositoryEvent$EventType METADATA_RESOLVED = new RepositoryEvent$EventType("METADATA_RESOLVED", 6);
   public static final RepositoryEvent$EventType ARTIFACT_DOWNLOADING = new RepositoryEvent$EventType("ARTIFACT_DOWNLOADING", 7);
   public static final RepositoryEvent$EventType ARTIFACT_DOWNLOADED = new RepositoryEvent$EventType("ARTIFACT_DOWNLOADED", 8);
   public static final RepositoryEvent$EventType METADATA_DOWNLOADING = new RepositoryEvent$EventType("METADATA_DOWNLOADING", 9);
   public static final RepositoryEvent$EventType METADATA_DOWNLOADED = new RepositoryEvent$EventType("METADATA_DOWNLOADED", 10);
   public static final RepositoryEvent$EventType ARTIFACT_INSTALLING = new RepositoryEvent$EventType("ARTIFACT_INSTALLING", 11);
   public static final RepositoryEvent$EventType ARTIFACT_INSTALLED = new RepositoryEvent$EventType("ARTIFACT_INSTALLED", 12);
   public static final RepositoryEvent$EventType METADATA_INSTALLING = new RepositoryEvent$EventType("METADATA_INSTALLING", 13);
   public static final RepositoryEvent$EventType METADATA_INSTALLED = new RepositoryEvent$EventType("METADATA_INSTALLED", 14);
   public static final RepositoryEvent$EventType ARTIFACT_DEPLOYING = new RepositoryEvent$EventType("ARTIFACT_DEPLOYING", 15);
   public static final RepositoryEvent$EventType ARTIFACT_DEPLOYED = new RepositoryEvent$EventType("ARTIFACT_DEPLOYED", 16);
   public static final RepositoryEvent$EventType METADATA_DEPLOYING = new RepositoryEvent$EventType("METADATA_DEPLOYING", 17);
   public static final RepositoryEvent$EventType METADATA_DEPLOYED = new RepositoryEvent$EventType("METADATA_DEPLOYED", 18);
   private static final RepositoryEvent$EventType[] $VALUES = new RepositoryEvent$EventType[]{ARTIFACT_DESCRIPTOR_INVALID, ARTIFACT_DESCRIPTOR_MISSING, METADATA_INVALID, ARTIFACT_RESOLVING, ARTIFACT_RESOLVED, METADATA_RESOLVING, METADATA_RESOLVED, ARTIFACT_DOWNLOADING, ARTIFACT_DOWNLOADED, METADATA_DOWNLOADING, METADATA_DOWNLOADED, ARTIFACT_INSTALLING, ARTIFACT_INSTALLED, METADATA_INSTALLING, METADATA_INSTALLED, ARTIFACT_DEPLOYING, ARTIFACT_DEPLOYED, METADATA_DEPLOYING, METADATA_DEPLOYED};

   public static RepositoryEvent$EventType[] values() {
      return (RepositoryEvent$EventType[])$VALUES.clone();
   }

   public static RepositoryEvent$EventType valueOf(String var0) {
      return (RepositoryEvent$EventType)Enum.valueOf(RepositoryEvent$EventType.class, var0);
   }

   private RepositoryEvent$EventType(String var1, int var2) {
      super(var1, var2);
   }
}
