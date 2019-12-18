package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.nodes.NodeId;

class Serializer$1 {
   static final int[] $SwitchMap$org$yaml$snakeyaml$nodes$NodeId = new int[NodeId.values().length];

   static {
      try {
         $SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.sequence.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         $SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.mapping.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         $SwitchMap$org$yaml$snakeyaml$nodes$NodeId[NodeId.scalar.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

   }
}
