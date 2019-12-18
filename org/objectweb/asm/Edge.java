package org.objectweb.asm;

final class Edge {
   static final int JUMP = 0;
   static final int EXCEPTION = 0;
   final int info;
   final Label successor;
   Edge nextEdge;

   Edge(int var1, Label var2, Edge var3) {
      super();
      this.info = var1;
      this.successor = var2;
      this.nextEdge = var3;
   }
}
