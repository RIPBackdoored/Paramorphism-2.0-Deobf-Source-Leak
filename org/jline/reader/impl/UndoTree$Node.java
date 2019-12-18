package org.jline.reader.impl;

class UndoTree$Node {
   private final Object state;
   private UndoTree$Node left;
   private UndoTree$Node right;
   final UndoTree this$0;

   public UndoTree$Node(UndoTree var1, Object var2) {
      super();
      this.this$0 = var1;
      this.left = null;
      this.right = null;
      this.state = var2;
   }

   static UndoTree$Node access$002(UndoTree$Node var0, UndoTree$Node var1) {
      return var0.left = var1;
   }

   static UndoTree$Node access$102(UndoTree$Node var0, UndoTree$Node var1) {
      return var0.right = var1;
   }

   static UndoTree$Node access$000(UndoTree$Node var0) {
      return var0.left;
   }

   static UndoTree$Node access$100(UndoTree$Node var0) {
      return var0.right;
   }

   static Object access$200(UndoTree$Node var0) {
      return var0.state;
   }
}
