package org.yaml.snakeyaml.events;

public final class Event$ID extends Enum {
   public static final Event$ID Alias = new Event$ID("Alias", 0);
   public static final Event$ID DocumentEnd = new Event$ID("DocumentEnd", 1);
   public static final Event$ID DocumentStart = new Event$ID("DocumentStart", 2);
   public static final Event$ID MappingEnd = new Event$ID("MappingEnd", 3);
   public static final Event$ID MappingStart = new Event$ID("MappingStart", 4);
   public static final Event$ID Scalar = new Event$ID("Scalar", 5);
   public static final Event$ID SequenceEnd = new Event$ID("SequenceEnd", 6);
   public static final Event$ID SequenceStart = new Event$ID("SequenceStart", 7);
   public static final Event$ID StreamEnd = new Event$ID("StreamEnd", 8);
   public static final Event$ID StreamStart = new Event$ID("StreamStart", 9);
   private static final Event$ID[] $VALUES = new Event$ID[]{Alias, DocumentEnd, DocumentStart, MappingEnd, MappingStart, Scalar, SequenceEnd, SequenceStart, StreamEnd, StreamStart};

   public static Event$ID[] values() {
      return (Event$ID[])$VALUES.clone();
   }

   public static Event$ID valueOf(String var0) {
      return (Event$ID)Enum.valueOf(Event$ID.class, var0);
   }

   private Event$ID(String var1, int var2) {
      super(var1, var2);
   }
}
