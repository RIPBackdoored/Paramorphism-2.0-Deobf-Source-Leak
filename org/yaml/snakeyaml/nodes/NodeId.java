package org.yaml.snakeyaml.nodes;

public final class NodeId extends Enum {
   public static final NodeId scalar = new NodeId("scalar", 0);
   public static final NodeId sequence = new NodeId("sequence", 1);
   public static final NodeId mapping = new NodeId("mapping", 2);
   public static final NodeId anchor = new NodeId("anchor", 3);
   private static final NodeId[] $VALUES = new NodeId[]{scalar, sequence, mapping, anchor};

   public static NodeId[] values() {
      return (NodeId[])$VALUES.clone();
   }

   public static NodeId valueOf(String var0) {
      return (NodeId)Enum.valueOf(NodeId.class, var0);
   }

   private NodeId(String var1, int var2) {
      super(var1, var2);
   }
}
