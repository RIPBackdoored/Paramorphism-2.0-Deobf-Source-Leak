package org.eclipse.aether.metadata;

public final class Metadata$Nature extends Enum {
   public static final Metadata$Nature RELEASE = new Metadata$Nature("RELEASE", 0);
   public static final Metadata$Nature SNAPSHOT = new Metadata$Nature("SNAPSHOT", 1);
   public static final Metadata$Nature RELEASE_OR_SNAPSHOT = new Metadata$Nature("RELEASE_OR_SNAPSHOT", 2);
   private static final Metadata$Nature[] $VALUES = new Metadata$Nature[]{RELEASE, SNAPSHOT, RELEASE_OR_SNAPSHOT};

   public static Metadata$Nature[] values() {
      return (Metadata$Nature[])$VALUES.clone();
   }

   public static Metadata$Nature valueOf(String var0) {
      return (Metadata$Nature)Enum.valueOf(Metadata$Nature.class, var0);
   }

   private Metadata$Nature(String var1, int var2) {
      super(var1, var2);
   }
}
