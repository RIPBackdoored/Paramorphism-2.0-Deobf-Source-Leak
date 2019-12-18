package org.eclipse.aether.util.graph.transformer;

public final class TransformationContextKeys {
   public static final Object CONFLICT_IDS = "conflictIds";
   public static final Object SORTED_CONFLICT_IDS = "sortedConflictIds";
   public static final Object CYCLIC_CONFLICT_IDS = "cyclicConflictIds";
   public static final Object STATS = "stats";

   private TransformationContextKeys() {
      super();
   }
}
